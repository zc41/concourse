package org.cinchapi.concourse.server.concurrent;

/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013-2014 Jeff Nelson, Cinchapi Software Collective
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
import java.util.List;

import org.cinchapi.concourse.server.model.Value;
import org.cinchapi.concourse.thrift.Operator;
import org.cinchapi.concourse.util.Range;

import com.google.common.collect.Lists;

/**
 * A collection of utility functions for dealing with {@link RangeToken}
 * objects.
 * 
 * @author jnelson
 */
public final class RangeTokens {

    /**
     * Convert the {@code rangeToken} to the analogous {@link Range} objects.
     * <p>
     * NOTE: This method returns an iterable because a NOT_EQUALS RangeToken is
     * split into two discreet ranges. If you know the RangeToken does not have
     * a NOT_EQUALS operator, you can assume that the returned Iterable only has
     * one element.
     * </p>
     * 
     * @param token
     * @return the Range
     */
    public static Iterable<Range> convertToRange(RangeToken token) {
        List<Range> ranges = Lists.newArrayListWithCapacity(1);
        if(token.getOperator() == Operator.EQUALS
                || token.getOperator() == null) { // null operator means
                                                  // the range token is for
                                                  // writing
            ranges.add(Range.point(token.getValues()[0]));
        }
        else if(token.getOperator() == Operator.NOT_EQUALS) {
            ranges.add(Range.lessThan(token.getValues()[0]));
            ranges.add(Range.greaterThan(token.getValues()[0]));
        }
        else if(token.getOperator() == Operator.GREATER_THAN) {
            ranges.add(Range.greaterThan(token.getValues()[0]));
        }
        else if(token.getOperator() == Operator.GREATER_THAN_OR_EQUALS) {
            ranges.add(Range.atLeast(token.getValues()[0]));
        }
        else if(token.getOperator() == Operator.LESS_THAN) {
            ranges.add(Range.lessThan(Value.NEGATIVE_INFINITY));
        }
        else if(token.getOperator() == Operator.LESS_THAN_OR_EQUALS) {
            ranges.add(Range.atMost(token.getValues()[0]));
        }
        else if(token.getOperator() == Operator.BETWEEN) {
            Value a = token.getValues()[0];
            Value b = token.getValues()[1];
            if(a == Value.NEGATIVE_INFINITY && b == Value.POSITIVE_INFINITY) {
                ranges.add(Range.all());
            }
            else {
                ranges.add(Range.between(a, b));
            }
        }
        else if(token.getOperator() == Operator.REGEX
                || token.getOperator() == Operator.NOT_REGEX) {
            ranges.add(Range.all());
        }
        else {
            throw new UnsupportedOperationException();
        }
        return ranges;
    }

}
