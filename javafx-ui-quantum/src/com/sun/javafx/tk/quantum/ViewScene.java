/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.sun.javafx.tk.quantum;

import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Cursor;
import com.sun.glass.ui.Pixels;
import com.sun.glass.ui.View;
import com.sun.glass.ui.Window;
import com.sun.javafx.cursor.CursorFrame;
import com.sun.javafx.sg.PGNode;
import com.sun.javafx.sg.prism.NGNode;
import com.sun.javafx.tk.Toolkit;
import com.sun.prism.render.ToolkitInterface;

class ViewScene extends GlassScene {

    private static final String UNSUPPORTED_FORMAT = 
        "Transparent windows only supported for BYTE_BGRA_PRE format on LITTLE_ENDIAN machines";

    private View platformView;
    private ViewPainter painter;

    private AtomicBoolean painting = new AtomicBoolean(false);
    private PaintRenderJob paintRenderJob;

    public ViewScene(boolean verbose, boolean depthBuffer) {
        super(verbose, depthBuffer);

        this.platformView = Application.GetApplication().createView();
        this.platformView.setEventHandler(new GlassViewEventHandler(this));
    }

    @Override protected boolean isSynchronous() {
        return painter != null && painter instanceof PresentingPainter;
    }

    protected View getPlatformView() {
        return this.platformView;
    }
    
    ViewPainter getPainter() {
        return painter;
    }

    boolean setPainting(boolean value) {
        return painting.getAndSet(value);
    }

    @Override
    public void setGlassStage(GlassStage stage) {
        super.setGlassStage(stage);
        if (stage != null) {
            WindowStage wstage  = (WindowStage)stage;
            if (wstage.needsUpdateWindow()) {
                if (Pixels.getNativeFormat() != Pixels.Format.BYTE_BGRA_PRE ||
                    ByteOrder.nativeOrder() != ByteOrder.LITTLE_ENDIAN) {
                    throw new UnsupportedOperationException(UNSUPPORTED_FORMAT);
                }
                painter = new UploadingPainter(this);
            } else {
                painter = new PresentingPainter(this);
            }
//            pen.setPainter(painter);
            painter.setRoot((NGNode)getRoot());
            paintRenderJob = new PaintRenderJob(this, PaintCollector.getInstance().getRendered(), painter);
        }
    }

    WindowStage getWindowStage() {
        return (WindowStage)glassStage;
    }

    /* com.sun.javafx.tk.TKScene */

    @Override public void setScene(Object scene) {
        if (scene == null) {
            // Setting scene to null is a dispose operation
            if (this.platformView != null) {
                AbstractPainter.renderLock.lock();
                try {
                    this.platformView.close();
                    this.platformView = null;
                    this.updateViewState();
                } finally {
                    AbstractPainter.renderLock.unlock();
                }
            }
        }
    }
    
    @Override public void setRoot(PGNode root) {
        super.setRoot(root);
        if (painter != null) {
            painter.setRoot((NGNode)root);
        }
    }

    @Override
    public void setCursor(final Object cursor) {
        super.setCursor(cursor);
        Application.invokeLater(new Runnable() {
            @Override
            public void run() {
                final CursorFrame cursorFrame = (CursorFrame) cursor;
                final Cursor platformCursor =
                        CursorUtils.getPlatformCursor(cursorFrame);
                
                if (platformView != null) {
                    Window window = platformView.getWindow();
                    if (window != null) {
                        window.setCursor(platformCursor);
                    }
                }
            }
        });
    }

    @Override void repaint() {
        if (platformView == null) {
            return;
        }

        if (!painting.getAndSet(true)) {
            Toolkit tk = Toolkit.getToolkit();
            ToolkitInterface toolkit = (ToolkitInterface)tk;
            toolkit.addRenderJob(paintRenderJob);
        }
    }
    
    @Override
    public void enableInputMethodEvents(boolean enable) {
        platformView.enableInputMethodEvents(enable);
    }

    @Override public String toString() {
        View view = getPlatformView();
        return (" scene: " + hashCode() + " @ (" + view.getWidth() + "," + view.getHeight() + ")");
    }
}
