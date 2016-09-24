/*
 * Copyright (C) 2015 Apple Inc. All rights reserved.
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

#ifndef MediaPlaybackTargetMac_h
#define MediaPlaybackTargetMac_h

#if ENABLE(WIRELESS_PLAYBACK_TARGET)

#include "MediaPlaybackTarget.h"
#include <wtf/RetainPtr.h>

namespace WebCore {

class MediaPlaybackTargetMac : public MediaPlaybackTarget {
public:
    WEBCORE_EXPORT static Ref<MediaPlaybackTarget> create(AVOutputContext *);

    virtual ~MediaPlaybackTargetMac();

    virtual TargetType targetType() const { return AVFoundation; }

    virtual const MediaPlaybackTargetContext& targetContext() const;
    virtual bool hasActiveRoute() const;

    AVOutputContext *outputContext() const { return m_outputContext.get(); }

protected:
    MediaPlaybackTargetMac(AVOutputContext *);

    RetainPtr<AVOutputContext> m_outputContext;
    mutable MediaPlaybackTargetContext m_context;
};

MediaPlaybackTargetMac* toMediaPlaybackTargetMac(MediaPlaybackTarget*);
const MediaPlaybackTargetMac* toMediaPlaybackTargetMac(const MediaPlaybackTarget*);

}

#endif // ENABLE(WIRELESS_PLAYBACK_TARGET)

#endif
