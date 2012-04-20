/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.javafx.scene.control.cell;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * A class containing a {@link TableCell} implementation that draws a 
 * {@link CheckBox} node inside the cell, optionally with a label to indicate 
 * what the checkbox represents.
 * 
 * <p>By default, the CheckBoxTableCell is rendered with a CheckBox centred in 
 * the TableColumn. If a label is required, it is necessary to provide a 
 * non-null StringConverter instance to the 
 * {@link #CheckBoxTableCell(Callback, StringConverter)} constructor.
 * 
 * <p>To construct an instance of this class, it is necessary to provide a 
 * {@link Callback} that, given an object of type T, will return an 
 * {@code ObservableProperty<Boolean>} that represents whether the given item is
 * selected or not. This ObservableValue will be bound bidirectionally (meaning 
 * that the CheckBox in the cell will set/unset this property based on user 
 * interactions, and the CheckBox will reflect the state of the ObservableValue, 
 * if it changes externally).
 * 
 * @param <T> The type of the elements contained within the TableColumn.
 */
public class CheckBoxTableCell<S,T> extends TableCell<S,T> {
    private final CheckBox checkBox;
    
    private final boolean showLabel;
    
    private final StringConverter<T> converter;
    
    private final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty;
    private ObservableValue<Boolean> booleanProperty;
    
    private CheckBoxTableCell() {
        this(null, null);
    }
    
    /**
     * Creates a default CheckBoxTableCell with a custom {@link Callback} to 
     * retrieve an ObservableValue for a given cell index.
     * 
     * @param getSelectedProperty A {@link Callback} that will return an {@link 
     *      ObservableValue} given an index from the TableColumn.
     */
    public CheckBoxTableCell(
            final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty) {
        this(getSelectedProperty, null);
    }

    /**
     * Creates a CheckBoxTableCell with a custom string converter.
     * 
     * @param getSelectedProperty A {@link Callback} that will return a {@link 
     *      ObservableValue} given an index from the TableColumn.
     * @param converter A StringConverter that, given an object of type T, will return a 
     *      String that can be used to represent the object visually.
     */
    public CheckBoxTableCell(
            final Callback<Integer, ObservableValue<Boolean>> getSelectedProperty, 
            final StringConverter<T> converter) {
        // we let getSelectedProperty be null here, as we can always defer to the
        // TableColumn
        this.getSelectedProperty = getSelectedProperty;
        this.converter = converter;
        this.showLabel = converter != null;
        
        this.checkBox = new CheckBox();
//        this.checkBox.setAlignment(Pos.TOP_CENTER);
        
        setAlignment(Pos.CENTER);
        setGraphic(checkBox);
        
        if (showLabel) {
            this.checkBox.setAlignment(Pos.CENTER_LEFT);
        }
    }
    
    /** {@inheritDoc} */
    @Override public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (showLabel) {
                setText(converter.toString(item));
            }
            setGraphic(checkBox);
            
            if (booleanProperty instanceof BooleanProperty) {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty)booleanProperty);
            }
            ObservableValue obsValue = getSelectedProperty();
            if (obsValue instanceof BooleanProperty) {
                booleanProperty = obsValue;
                checkBox.selectedProperty().bindBidirectional((BooleanProperty)booleanProperty);
            }
            
            checkBox.disableProperty().bind(Bindings.not(
                    getTableView().editableProperty().and(
                    getTableColumn().editableProperty()).and(
                    editableProperty())
                ));
        }
    }
    
    private ObservableValue getSelectedProperty() {
        return getSelectedProperty != null ?
                getSelectedProperty.call(getIndex()) :
                getTableColumn().getCellObservableValue(getIndex());
    }
}