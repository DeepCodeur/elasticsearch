/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.ml.dataframe.stats;

import org.elasticsearch.xpack.core.ml.dataframe.stats.common.DataCounts;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class DataCountsTracker {

    private final String jobId;
    private AtomicLong trainingDocsCount;
    private AtomicLong testDocsCount;
    private AtomicLong skippedDocsCount;

    public DataCountsTracker(DataCounts dataCounts) {
        this.jobId = Objects.requireNonNull(dataCounts.getJobId());
        this.trainingDocsCount.set(dataCounts.getTrainingDocsCount());
        this.testDocsCount.set(dataCounts.getTestDocsCount());
        this.skippedDocsCount.set(dataCounts.getSkippedDocsCount());
    }

    public synchronized void incrementTrainingDocsCount() {
        trainingDocsCount.set(trainingDocsCount.get() + 1);
    }

    public synchronized void incrementTestDocsCount() {
        testDocsCount.set(testDocsCount.get() + 1);
    }

    public synchronized void incrementSkippedDocsCount() {
        skippedDocsCount.set(skippedDocsCount.get() + 1);
    }

    public DataCounts report() {
        return new DataCounts(
            jobId,
            trainingDocsCount.get(),
            testDocsCount.get(),
            skippedDocsCount.get()
        );
    }

    public void reset() {
        trainingDocsCount.set(0);
        testDocsCount.set(0);
        skippedDocsCount.set(0);
    }

    public void setTestDocsCount(long testDocsCount) {
        this.testDocsCount.set(testDocsCount);
    }
}
