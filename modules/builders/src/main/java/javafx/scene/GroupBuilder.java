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

package javafx.scene;

/**
Builder class for javafx.scene.Group
@see javafx.scene.Group
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.0
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class GroupBuilder<B extends javafx.scene.GroupBuilder<B>> extends javafx.scene.ParentBuilder<B> implements javafx.util.Builder<javafx.scene.Group> {
    protected GroupBuilder() {
    }
    
    /** Creates a new instance of GroupBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.scene.GroupBuilder<?> create() {
        return new javafx.scene.GroupBuilder();
    }
    
    private int __set;
    public void applyTo(javafx.scene.Group x) {
        super.applyTo(x);
        int set = __set;
        if ((set & (1 << 0)) != 0) x.setAutoSizeChildren(this.autoSizeChildren);
        if ((set & (1 << 1)) != 0) x.getChildren().addAll(this.children);
    }
    
    private boolean autoSizeChildren;
    /**
    Set the value of the {@link javafx.scene.Group#isAutoSizeChildren() autoSizeChildren} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B autoSizeChildren(boolean x) {
        this.autoSizeChildren = x;
        __set |= 1 << 0;
        return (B) this;
    }
    
    private java.util.Collection<? extends javafx.scene.Node> children;
    /**
    Add the given items to the List of items in the {@link javafx.scene.Group#getChildren() children} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B children(java.util.Collection<? extends javafx.scene.Node> x) {
        this.children = x;
        __set |= 1 << 1;
        return (B) this;
    }
    
    /**
    Add the given items to the List of items in the {@link javafx.scene.Group#getChildren() children} property for the instance constructed by this builder.
    */
    public B children(javafx.scene.Node... x) {
        return children(java.util.Arrays.asList(x));
    }
    
    /**
    Make an instance of {@link javafx.scene.Group} based on the properties set on this builder.
    */
    public javafx.scene.Group build() {
        javafx.scene.Group x = new javafx.scene.Group();
        applyTo(x);
        return x;
    }
}
