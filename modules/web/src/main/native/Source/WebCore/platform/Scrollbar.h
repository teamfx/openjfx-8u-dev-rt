/*
 * Copyright (C) 2004, 2006, 2014-2015 Apple Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY APPLE INC. ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL APPLE INC. OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

#ifndef Scrollbar_h
#define Scrollbar_h

#include "ScrollTypes.h"
#include "Timer.h"
#include "Widget.h"
#include <wtf/PassRefPtr.h>
#include <wtf/WeakPtr.h>

namespace WebCore {

class GraphicsContext;
class IntRect;
class PlatformMouseEvent;
class ScrollableArea;
class ScrollbarTheme;

class Scrollbar : public Widget {
public:
    // Must be implemented by platforms that can't simply use the Scrollbar base class.  Right now the only platform that is not using the base class is GTK.
    WEBCORE_EXPORT static PassRefPtr<Scrollbar> createNativeScrollbar(ScrollableArea&, ScrollbarOrientation, ScrollbarControlSize);

    virtual ~Scrollbar();

    // Called by the ScrollableArea when the scroll offset changes.
    void offsetDidChange();

    static int pixelsPerLineStep() { return 40; }
    static float minFractionToStepWhenPaging() { return 0.8; }
    WEBCORE_EXPORT static int maxOverlapBetweenPages();
    static int pageStep(int viewWidthOrHeight, int contentWidthOrHeight) { return std::max(std::max<int>(lroundf(viewWidthOrHeight * Scrollbar::minFractionToStepWhenPaging()), lroundf(contentWidthOrHeight - Scrollbar::maxOverlapBetweenPages())), 1); }
    static int pageStep(int viewWidthOrHeight) { return pageStep(viewWidthOrHeight, viewWidthOrHeight); }
    static float pageStepDelta(int widthOrHeight) { return std::max(std::max(static_cast<float>(widthOrHeight) * Scrollbar::minFractionToStepWhenPaging(), static_cast<float>(widthOrHeight) - Scrollbar::maxOverlapBetweenPages()), 1.0f); }

    ScrollableArea& scrollableArea() const { return m_scrollableArea; }

    bool isCustomScrollbar() const { return m_isCustomScrollbar; }
    ScrollbarOrientation orientation() const { return m_orientation; }

    int value() const { return lroundf(m_currentPos); }
    float currentPos() const { return m_currentPos; }
    int pressedPos() const { return m_pressedPos; }
    int visibleSize() const { return m_visibleSize; }
    int totalSize() const { return m_totalSize; }
    int maximum() const { return m_totalSize - m_visibleSize; }
    ScrollbarControlSize controlSize() const { return m_controlSize; }

    int occupiedWidth() const;
    int occupiedHeight() const;

    int lineStep() const { return m_lineStep; }
    int pageStep() const { return m_pageStep; }
    float pixelStep() const { return m_pixelStep; }

    ScrollbarPart pressedPart() const { return m_pressedPart; }
    ScrollbarPart hoveredPart() const { return m_hoveredPart; }
    virtual void setHoveredPart(ScrollbarPart);
    virtual void setPressedPart(ScrollbarPart);

    WEBCORE_EXPORT void setSteps(int lineStep, int pageStep, int pixelsPerStep = 1);
    WEBCORE_EXPORT void setProportion(int visibleSize, int totalSize);
    void setPressedPos(int p) { m_pressedPos = p; }

    virtual void paint(GraphicsContext&, const IntRect& damageRect) override;

    bool enabled() const { return m_enabled; }
    virtual void setEnabled(bool);

    virtual bool isOverlayScrollbar() const;
    bool shouldParticipateInHitTesting();

    bool isWindowActive() const;

    // These methods are used for platform scrollbars to give :hover feedback.  They will not get called
    // when the mouse went down in a scrollbar, since it is assumed the scrollbar will start
    // grabbing all events in that case anyway.
#if !PLATFORM(IOS)
    WEBCORE_EXPORT bool mouseMoved(const PlatformMouseEvent&);
#endif
    WEBCORE_EXPORT void mouseEntered();
    WEBCORE_EXPORT bool mouseExited();

    // Used by some platform scrollbars to know when they've been released from capture.
    WEBCORE_EXPORT bool mouseUp(const PlatformMouseEvent&);

    WEBCORE_EXPORT bool mouseDown(const PlatformMouseEvent&);

    ScrollbarTheme& theme() const { return m_theme; }

    virtual void invalidateRect(const IntRect&) override;

    bool suppressInvalidation() const { return m_suppressInvalidation; }
    void setSuppressInvalidation(bool s) { m_suppressInvalidation = s; }

    virtual void styleChanged() { }

    virtual IntRect convertToContainingView(const IntRect&) const override;
    virtual IntRect convertFromContainingView(const IntRect&) const override;

    virtual IntPoint convertToContainingView(const IntPoint&) const override;
    virtual IntPoint convertFromContainingView(const IntPoint&) const override;

    void moveThumb(int pos, bool draggingDocument = false);

    bool isAlphaLocked() const { return m_isAlphaLocked; }
    void setIsAlphaLocked(bool flag) { m_isAlphaLocked = flag; }

    float opacity() const { return m_opacity; }
    void setOpacity(float opacity) { m_opacity = opacity; }

    bool supportsUpdateOnSecondaryThread() const;

    WeakPtr<Scrollbar> createWeakPtr() { return m_weakPtrFactory.createWeakPtr(); }

protected:
    Scrollbar(ScrollableArea&, ScrollbarOrientation, ScrollbarControlSize, ScrollbarTheme* = 0, bool isCustomScrollbar = false);

    void updateThumb();
    virtual void updateThumbPosition();
    virtual void updateThumbProportion();

    void autoscrollTimerFired();
    void startTimerIfNeeded(double delay);
    void stopTimerIfNeeded();
    void autoscrollPressedPart(double delay);
    ScrollDirection pressedPartScrollDirection();
    ScrollGranularity pressedPartScrollGranularity();

    ScrollableArea& m_scrollableArea;
    ScrollbarOrientation m_orientation;
    ScrollbarControlSize m_controlSize;
    ScrollbarTheme& m_theme;

    int m_visibleSize;
    int m_totalSize;
    float m_currentPos;
    float m_dragOrigin;
    int m_lineStep;
    int m_pageStep;
    float m_pixelStep;

    ScrollbarPart m_hoveredPart;
    ScrollbarPart m_pressedPart;
    int m_pressedPos;
    float m_scrollPos;
    bool m_draggingDocument;
    int m_documentDragPos;

    bool m_enabled;

    Timer m_scrollTimer;

    bool m_suppressInvalidation;

    bool m_isAlphaLocked;

    bool m_isCustomScrollbar;

    float m_opacity { 1 };

private:
    virtual bool isScrollbar() const override { return true; }

    WeakPtrFactory<Scrollbar> m_weakPtrFactory;
};

} // namespace WebCore

SPECIALIZE_TYPE_TRAITS_WIDGET(Scrollbar, isScrollbar())

#endif // Scrollbar_h
