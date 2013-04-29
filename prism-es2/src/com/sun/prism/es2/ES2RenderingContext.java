/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.prism.es2;

import com.sun.prism.PresentableState;
import com.sun.prism.impl.BaseRenderingContext;

public class ES2RenderingContext extends BaseRenderingContext {

    private final ES2Context context;
    private final GLDrawable drawable;

    ES2RenderingContext(ES2Context context, PresentableState pState) {
        this.context = context;
        if (pState != null) {
            long nativeWindow = pState.getNativeWindow();
            drawable = ES2Pipeline.glFactory.createGLDrawable(nativeWindow,
                    context.getPixelFormat());
        } else {
            drawable = context.getDummyDrawable();
        }
    }

    /**
     * Makes the rendering context current.
     */
    @Override
    public void begin() {
        context.setCurrentRenderingContext(this, drawable);
    }

    /**
     * Releases the rendering context and restores the previously
     * current rendering context.
     */
    @Override
    public void end() {
        context.setCurrentRenderingContext(null, context.getDummyDrawable());
    }

    GLDrawable getDrawable() {
        return drawable;
    }
    }