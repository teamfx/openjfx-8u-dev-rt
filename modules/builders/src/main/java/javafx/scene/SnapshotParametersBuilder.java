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

package javafx.scene;

/**
Builder class for javafx.scene.SnapshotParameters
@see javafx.scene.SnapshotParameters
@deprecated This class is deprecated and will be removed in the next version
* @since JavaFX 2.2
*/
@javax.annotation.Generated("Generated by javafx.builder.processor.BuilderProcessor")
@Deprecated
public class SnapshotParametersBuilder<B extends javafx.scene.SnapshotParametersBuilder<B>> implements javafx.util.Builder<javafx.scene.SnapshotParameters> {
    protected SnapshotParametersBuilder() {
    }

    /** Creates a new instance of SnapshotParametersBuilder. */
    @SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
    public static javafx.scene.SnapshotParametersBuilder<?> create() {
        return new javafx.scene.SnapshotParametersBuilder();
    }

    private int __set;
    public void applyTo(javafx.scene.SnapshotParameters x) {
        int set = __set;
        if ((set & (1 << 0)) != 0) x.setCamera(this.camera);
        if ((set & (1 << 1)) != 0) x.setDepthBuffer(this.depthBuffer);
        if ((set & (1 << 2)) != 0) x.setFill(this.fill);
        if ((set & (1 << 3)) != 0) x.setTransform(this.transform);
        if ((set & (1 << 4)) != 0) x.setViewport(this.viewport);
    }

    private javafx.scene.Camera camera;
    /**
    Set the value of the {@link javafx.scene.SnapshotParameters#getCamera() camera} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B camera(javafx.scene.Camera x) {
        this.camera = x;
        __set |= 1 << 0;
        return (B) this;
    }

    private boolean depthBuffer;
    /**
    Set the value of the {@link javafx.scene.SnapshotParameters#isDepthBuffer() depthBuffer} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B depthBuffer(boolean x) {
        this.depthBuffer = x;
        __set |= 1 << 1;
        return (B) this;
    }

    private javafx.scene.paint.Paint fill;
    /**
    Set the value of the {@link javafx.scene.SnapshotParameters#getFill() fill} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B fill(javafx.scene.paint.Paint x) {
        this.fill = x;
        __set |= 1 << 2;
        return (B) this;
    }

    private javafx.scene.transform.Transform transform;
    /**
    Set the value of the {@link javafx.scene.SnapshotParameters#getTransform() transform} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B transform(javafx.scene.transform.Transform x) {
        this.transform = x;
        __set |= 1 << 3;
        return (B) this;
    }

    private javafx.geometry.Rectangle2D viewport;
    /**
    Set the value of the {@link javafx.scene.SnapshotParameters#getViewport() viewport} property for the instance constructed by this builder.
    */
    @SuppressWarnings("unchecked")
    public B viewport(javafx.geometry.Rectangle2D x) {
        this.viewport = x;
        __set |= 1 << 4;
        return (B) this;
    }

    /**
    Make an instance of {@link javafx.scene.SnapshotParameters} based on the properties set on this builder.
    */
    public javafx.scene.SnapshotParameters build() {
        javafx.scene.SnapshotParameters x = new javafx.scene.SnapshotParameters();
        applyTo(x);
        return x;
    }
}
