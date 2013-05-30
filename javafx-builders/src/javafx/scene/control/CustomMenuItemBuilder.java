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

package javafx.scene.control;

/**
Builder class for javafx.scene.control.CustomMenuItem
@see javafx.scene.control.CustomMenuItem
@deprecated This class is deprecated and will be removed in the next version
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class CustomMenuItemBuilder<B extends javafx.scene.control.CustomMenuItemBuilder<B>> extends javafx.scene.control.MenuItemBuilder<B> {
    protected CustomMenuItemBuilder() {
    }
    
    /** Creates a new instance of CustomMenuItemBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.scene.control.CustomMenuItemBuilder<?> create() {
        return new javafx.scene.control.CustomMenuItemBuilder();
    }
    
    private int __set;
    public void applyTo(javafx.scene.control.CustomMenuItem x) {
        super.applyTo(x);
        int set = __set;
        if ((set & (1 << 0)) != 0) x.setContent(this.content);
        if ((set & (1 << 1)) != 0) x.setHideOnClick(this.hideOnClick);
    }
    
    private javafx.scene.Node content;
    /**
    Set the value of the {@link javafx.scene.control.CustomMenuItem#getContent() content} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B content(javafx.scene.Node x) {
        this.content = x;
        __set |= 1 << 0;
        return (B) this;
    }
    
    private boolean hideOnClick;
    /**
    Set the value of the {@link javafx.scene.control.CustomMenuItem#isHideOnClick() hideOnClick} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B hideOnClick(boolean x) {
        this.hideOnClick = x;
        __set |= 1 << 1;
        return (B) this;
    }
    
    /**
    Make an instance of {@link javafx.scene.control.CustomMenuItem} based on the properties set on this builder.
    */
    public javafx.scene.control.CustomMenuItem build() {
        javafx.scene.control.CustomMenuItem x = new javafx.scene.control.CustomMenuItem();
        applyTo(x);
        return x;
    }
}