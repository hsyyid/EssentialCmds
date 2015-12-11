/*
 * This file is part of EssentialCmds, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2015 - 2015 HassanS6000
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.hsyyid.essentialcmds.api.util.config;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;

import java.util.Optional;

/**
 * Handles the detection of {@link NullPointerException} in CommentedConfigurationNode using {@link java.util.Optional}
 */
public class ConfigManager {

    public ConfigManager() {}

    /**
     * Get boolean {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional.empty.
     *
     * @param node The node for which the boolean value is needed
     * @return Optional.empty if null or Optional#of(Boolean) if not
     */
    public Optional<Boolean> getBoolean(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getBoolean());
    }

    /**
     * Get double {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional.empty.
     *
     * @param node The node for which the double value is needed
     * @return Optional.empty if null or Optional#of(Double) if not
     */
    public Optional<Double> getDouble(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getDouble());
    }

    /**
     * Get float {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
     *
     * @param node The node for which the float value is needed
     * @return Optional#empty if null or double value Optional#of(Float) if not
     */
    public Optional<Float> getFloat(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getFloat());
    }

    /**
     * Get int {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
     *
     * @param node The node for which the int value is needed
     * @return Optional#empty if null or Optional#of(Integer) if not
     */
    public Optional<Integer> getInt(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getInt());
    }

    /**
     * Get long {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
     *
     * @param node The node for which the long value is needed
     * @return Optional#empty if null or Optional#of(Long) if not
     */
    public Optional<Long> getLong(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getLong());
    }

    /**
     * Get String {@link Optional} value for CommentedConfigurationNode if it is not null; Otherwise return Optional#empty.
     *
     * @param node The node for which the String value is needed
     * @return Optional#empty if null or Optional#of(String) if not
     */
    public Optional<String> getString(CommentedConfigurationNode node){
        if (checkNull(node))
            return Optional.empty();
        return Optional.of(node.getString());
    }

    public Optional<Object> getValue(CommentedConfigurationNode node) {
        if (checkNull(node))
            return Optional.empty();
        Optional.of(node.getValue());
    }

    private boolean checkNull(CommentedConfigurationNode node) {
        if (node.getValue() == null)
            return true;
        return false;
    }
}
