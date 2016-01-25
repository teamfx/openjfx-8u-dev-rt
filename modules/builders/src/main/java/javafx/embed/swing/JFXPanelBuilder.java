/*
 * Copyright (c) 2011, 2014, Oracle and/or its affiliates. All rights reserved.
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

package javafx.embed.swing;

/**
Builder class for javafx.embed.swing.JFXPanel
@see javafx.embed.swing.JFXPanel
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.0
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class JFXPanelBuilder<B extends javafx.embed.swing.JFXPanelBuilder<B>> implements javafx.util.Builder<javafx.embed.swing.JFXPanel> {
    protected JFXPanelBuilder() {
    }

    /** Creates a new instance of JFXPanelBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.embed.swing.JFXPanelBuilder<?> create() {
        return new javafx.embed.swing.JFXPanelBuilder();
    }

    private int __set;
    public void applyTo(javafx.embed.swing.JFXPanel x) {
        int set = __set;
        if ((set & (1 << 0)) != 0) x.setOpaque(this.opaque);
        if ((set & (1 << 1)) != 0) x.setScene(this.scene);
    }

    private boolean opaque;
    /**
    Set the value of the {@link javafx.embed.swing.JFXPanel#isOpaque() opaque} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B opaque(boolean x) {
        this.opaque = x;
        __set |= 1 << 0;
        return (B) this;
    }

    private javafx.scene.Scene scene;
    /**
    Set the value of the {@link javafx.embed.swing.JFXPanel#getScene() scene} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B scene(javafx.scene.Scene x) {
        this.scene = x;
        __set |= 1 << 1;
        return (B) this;
    }

    /**
    Make an instance of {@link javafx.embed.swing.JFXPanel} based on the properties set on this builder.
    */
    public javafx.embed.swing.JFXPanel build() {
        javafx.embed.swing.JFXPanel x = new javafx.embed.swing.JFXPanel();
        applyTo(x);
        return x;
    }
}
