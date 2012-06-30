/*
 * Copyright (c) jmelzer 2012.
 * All rights reserved.
 */

package com.jmelzer.data.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

/**
* User: jmelzer
* Date: 19.12.11
* Time: 14:28
*/
public class DateIterator implements Iterator<Date>, Iterable<Date> {

    private Calendar end = Calendar.getInstance();
    private Calendar current = Calendar.getInstance();

    public DateIterator(Date start, Date end) {
        this.end.setTime(end);
        this.end.add(Calendar.DATE, -1);
        this.current.setTime(start);
        this.current.add(Calendar.DATE, -1);
    }

    public boolean hasNext() {
        // return !current.after(end);
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b = false;
        b1 = current.get(Calendar.YEAR) > end.get(Calendar.YEAR);
        b2 = current.get(Calendar.YEAR) == end.get(Calendar.YEAR);
        b3 = current.get(Calendar.DAY_OF_YEAR) > end.get(Calendar.DAY_OF_YEAR);
        b = b1 || (b2 && b3);
        return !b;
    }

    public Date next() {
        current.add(Calendar.DATE, 1);
        return current.getTime();
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove");
    }

    public Iterator<Date> iterator() {
        return this;
    }
}
