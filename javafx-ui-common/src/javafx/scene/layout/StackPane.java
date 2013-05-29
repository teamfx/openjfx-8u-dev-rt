/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javafx.scene.layout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.css.CssMetaData;
import javafx.css.StyleableObjectProperty;
import javafx.css.StyleableProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import com.sun.javafx.css.converters.EnumConverter;
import javafx.css.Styleable;
import javafx.geometry.HPos;
import javafx.util.Callback;

/**
 *
 * StackPane lays out its children in a back-to-front stack.
 * <p>
 * The z-order of the children is defined by the order of the children list
 * with the 0th child being the bottom and last child on top.  If a border and/or
 * padding have been set, the children will be layed out within those insets.
 * <p>
 * The stackpane will attempt to resize each child to fill its content area.
 * If the child could not be sized to fill the stackpane (either because it was
 * not resizable or its max size prevented it) then it will be aligned within
 * the area using the alignment property, which defaults to Pos.CENTER.
 * <p>
 * StackPane example:
 * <pre><code>     StackPane stack = new StackPane();
 *     stack.getChildren().addAll(new Rectangle(100,100,Color.BLUE), new Label("Go!));
 * </code></pre>
 * <p>
 * StackPane lays out each managed child regardless of the child's
 * visible property value; unmanaged children are ignored.</p>
 * <p>
 * StackPane may be styled with backgrounds and borders using CSS.  See
 * {@link javafx.scene.layout.Region Region} for details.</p>
 *
 * <h4>Resizable Range</h4>
 *
 * A stackpane's parent will resize the stackpane within the stackpane's resizable range
 * during layout.   By default the stackpane computes this range based on its content
 * as outlined in the table below.
 * <p>
 * <table border="1">
 * <tr><td></td><th>width</th><th>height</th></tr>
 * <tr><th>minimum</th>
 * <td>left/right insets plus the largest of the children's min widths.</td>
 * <td>top/bottom insets plus the largest of the children's min heights.</td></tr>
 * <tr><th>preferred</th>
 * <td>left/right insets plus the largest of the children's pref widths.</td>
 * <td>top/bottom insets plus the largest of the children's pref heights.</td></tr>
 * <tr><th>maximum</th>
 * <td>Double.MAX_VALUE</td><td>Double.MAX_VALUE</td></tr>
 * </table>
 * <p>
 * A stackpane's unbounded maximum width and height are an indication to the parent that
 * it may be resized beyond its preferred size to fill whatever space is assigned
 * to it.
 * <p>
 * StackPane provides properties for setting the size range directly.  These
 * properties default to the sentinel value USE_COMPUTED_SIZE, however the
 * application may set them to other values as needed:
 * <pre><code>     // ensure stackpane is never resized beyond it's preferred size
 *     <b>stackpane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);</b>
 * </code></pre>
 * Applications may restore the computed values by setting these properties back
 * to USE_COMPUTED_SIZE.
 *
 * <p>
 * StackPane does not clip its content by default, so it is possible that childrens'
 * bounds may extend outside its own bounds if a child's min size prevents it from
 * being fit within the stackpane.</p>
 *
 * <h4>Optional Layout Constraints</h4>
 *
 * An application may set constraints on individual children to customize StackPane's layout.
 * For each constraint, StackPane provides a static method for setting it on the child.
 * <p>
 * <table border="1">
 * <tr><th>Constraint</th><th>Type</th><th>Description</th></tr>
 * <tr><td>alignment</td><td>javafx.geometry.Pos</td><td>The alignment of the child within the stackpane.</td></tr>
 * <tr><td>margin</td><td>javafx.geometry.Insets</td><td>Margin space around the outside of the child.</td></tr>
 * </table>
 * <p>
 * Examples:
 * <pre><code>     // Align the title Label at the bottom-center of the stackpane
 *     Label title = new Label();
 *     <b>StackPane.setAlignment(title, Pos.BOTTOM_CENTER);</b>
 *     stackpane.getChildren.addAll(new ImageView(...), title);
 *
 *     // Create an 8 pixel margin around a listview in the stackpane
 *     ListView list = new ListView();
 *     <b>StackPane.setMargin(list, new Insets(8,8,8,8);</b>
 *     stackpane.getChildren().add(list);
 * </code></pre>
 *
 */

public class StackPane extends Pane {
    
    private boolean biasDirty = true;
    private boolean performingLayout = false;
    private Orientation bias;

    /********************************************************************
     *  BEGIN static methods
     ********************************************************************/

    private static final String MARGIN_CONSTRAINT = "stackpane-margin";
    private static final String ALIGNMENT_CONSTRAINT = "stackpane-alignment";

   /**
     * Sets the alignment for the child when contained by a stackpane.
     * If set, will override the stackpane's default alignment.
     * Setting the value to null will remove the constraint.
     * @param child the child node of a stackpane
     * @param value the alignment position for the child
     */
    public static void setAlignment(Node child, Pos value) {
        setConstraint(child, ALIGNMENT_CONSTRAINT, value);
    }

    /**
     * Returns the child's alignment constraint if set.
     * @param child the child node of a stackpane
     * @return the alignment position for the child or null if no alignment was set
     */
    public static Pos getAlignment(Node child) {
        return (Pos)getConstraint(child, ALIGNMENT_CONSTRAINT);
    }

    /**
     * Sets the margin for the child when contained by a stackpane.
     * If set, the stackpane will layout the child with the margin space around it.
     * Setting the value to null will remove the constraint.
     * @param child the child node of a stackpane
     * @param value the margin of space around the child
     */
    public static void setMargin(Node child, Insets value) {
        setConstraint(child, MARGIN_CONSTRAINT, value);
    }

    /**
     * Returns the child's margin constraints if set.
     * @param child the child node of a stackpane
     * @return the margin for the child or null if no margin was set
     */
    public static Insets getMargin(Node child) {
        return (Insets)getConstraint(child, MARGIN_CONSTRAINT);
    }

    private static final Callback<Node, Insets> marginAccessor = new Callback<Node, Insets>() {
        public Insets call(Node n) {
            return getMargin(n);
        }
    };

    /**
     * Removes all stackpane constraints from the child node.
     * @param child the child node
     */
    public static void clearConstraints(Node child) {
        setAlignment(child, null);
        setMargin(child, null);
    }
    /********************************************************************
     *  END static methods
     ********************************************************************/

    /**
     * Creates a StackPane layout with default CENTER alignment.
     */
    public StackPane() {
        super();
    }

    /**
     * Creates a StackPane layout with default CENTER alignment.
     * @param children The initial set of children for this pane.
     */
    public StackPane(Node... children) {
        super();
        getChildren().addAll(children);
    }

    /**
     * The default alignment of children within the stackpane's width and height.
     * This may be overridden on individual children by setting the child's
     * alignment constraint.
     */
    public final ObjectProperty<Pos> alignmentProperty() {
        if (alignment == null) {
            alignment = new StyleableObjectProperty<Pos>(Pos.CENTER) {
                @Override
                public void invalidated() {
                    requestLayout();
                }

                @Override
                public CssMetaData<StackPane, Pos> getCssMetaData() {
                    return StyleableProperties.ALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return StackPane.this;
                }

                @Override
                public String getName() {
                    return "alignment";
                }
            };
        }
        return alignment;
    }

    private ObjectProperty<Pos> alignment;
    public final void setAlignment(Pos value) { alignmentProperty().set(value); }
    public final Pos getAlignment() { return alignment == null ? Pos.CENTER : alignment.get(); }
    private Pos getAlignmentInternal() {
        Pos localPos = getAlignment();
        return localPos == null ? Pos.CENTER : localPos;
    }

    /**
     *
     * @return the first non-null contentBias of its managed children or null if no managed children
     * have a content bias.
     */
    @Override public Orientation getContentBias() {
        if (biasDirty) {
            final List<Node> children = getChildren();
            for (Node child : children) {
                Orientation contentBias = child.getContentBias();
                if (child.isManaged() && contentBias != null) {
                    bias = contentBias;
                    break;
                }
            }
            biasDirty = false;
        }        
        return bias;
    }

    @Override protected double computeMinWidth(double height) {
        List<Node>managed = getManagedChildren();
        return getInsets().getLeft() +
               computeMaxMinAreaWidth(managed, marginAccessor, getAlignmentInternal().getHpos(), height) +
               getInsets().getRight();
    }

    @Override protected double computeMinHeight(double width) {
        List<Node>managed = getManagedChildren();
        return getInsets().getTop() +
               computeMaxMinAreaHeight(managed, marginAccessor, getAlignmentInternal().getVpos(), width) +
               getInsets().getBottom();
    }

    @Override protected double computePrefWidth(double height) {
        List<Node>managed = getManagedChildren();
//        double h = -1;
//        boolean vertBias = false;
//        for (Node child: managed) {
//            if (child.getContentBias() == Orientation.VERTICAL) {
//                vertBias = true;
//                break;
//            }
//        }
//        if (vertBias) {
//            // widest may depend on height
//            h = computeMaxPrefAreaHeight(managed, margins, -1, getAlignment().getVpos());
//        }
        Insets padding = getInsets();
        return padding.getLeft() +
               computeMaxPrefAreaWidth(managed, marginAccessor,
                                       (height == -1) ? -1 : (height - padding.getTop() - padding.getBottom()),
                                       getAlignmentInternal().getHpos()) +
               padding.getRight();
    }

    @Override protected double computePrefHeight(double width) {
        List<Node>managed = getManagedChildren();
//        double w = -1;
//        boolean horizBias = false;
//        for (Node child: managed) {
//            if (child.getContentBias() == Orientation.HORIZONTAL) {
//                horizBias = true;
//                break;
//            }
//        }
//        if (horizBias) {
//            // tallest may depend on width of tile
//            w = computeMaxPrefAreaWidth(managed, margins, -1, getAlignment().getHpos());
//        }
        Insets padding = getInsets();
        return padding.getTop() +
               computeMaxPrefAreaHeight(managed, marginAccessor,
                                        (width == -1) ? -1 : (width - padding.getLeft() - padding.getRight()),
                                        getAlignmentInternal().getVpos()) +
               padding.getBottom();
    }


    @Override public void requestLayout() {
        if (performingLayout) {
            return;
        }
        biasDirty = true;
        bias = null;
        super.requestLayout();
    }

    @Override protected void layoutChildren() {
        performingLayout = true;
        List<Node> managed = getManagedChildren();
        Pos align = getAlignmentInternal();
        HPos alignHpos = align.getHpos();
        VPos alignVpos = align.getVpos();
        double width = getWidth();
        double height = getHeight();
        double top = getInsets().getTop();
        double right = getInsets().getRight();
        double left = getInsets().getLeft();
        double bottom = getInsets().getBottom();
        double baselineOffset = alignVpos == VPos.BASELINE ? getMaxBaselineOffset(managed)
                                    : height/2;
        double contentWidth = width - left - right;
        double contentHeight = height - top - bottom;
        for (int i = 0, size = managed.size(); i < size; i++) {
            Node child = managed.get(i);
            Pos childAlignment = StackPane.getAlignment(child);
            layoutInArea(child, left, top,
                           contentWidth, contentHeight,
                           baselineOffset, getMargin(child),
                           childAlignment != null? childAlignment.getHpos() : alignHpos,
                           childAlignment != null? childAlignment.getVpos() : alignVpos);
        }
        performingLayout = false;
    }

    /***************************************************************************
     *                                                                         *
     *                         Stylesheet Handling                             *
     *                                                                         *
     **************************************************************************/

     /**
      * Super-lazy instantiation pattern from Bill Pugh.
      * @treatAsPrivate implementation detail
      */
     private static class StyleableProperties {
         private static final CssMetaData<StackPane,Pos> ALIGNMENT =
             new CssMetaData<StackPane,Pos>("-fx-alignment",
                 new EnumConverter<Pos>(Pos.class),
                 Pos.CENTER) {

            @Override
            public boolean isSettable(StackPane node) {
                return node.alignment == null ||
                        !node.alignment.isBound();
            }

            @Override
            public StyleableProperty<Pos> getStyleableProperty(StackPane node) {
                return (StyleableProperty<Pos>)node.alignmentProperty();
            }
        };

         private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;
         static {
            final List<CssMetaData<? extends Styleable, ?>> styleables =
                new ArrayList<CssMetaData<? extends Styleable, ?>>(Region.getClassCssMetaData());
            styleables.add(ALIGNMENT);
            STYLEABLES = Collections.unmodifiableList(styleables);
         }
    }

    /**
     * @return The CssMetaData associated with this class, which may include the
     * CssMetaData of its super classes.
     */
    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return StyleableProperties.STYLEABLES;
    }

    /**
     * {@inheritDoc}
     *
     */


    @Override
    public List<CssMetaData<? extends Styleable, ?>> getCssMetaData() {
        return getClassCssMetaData();
    }

}
