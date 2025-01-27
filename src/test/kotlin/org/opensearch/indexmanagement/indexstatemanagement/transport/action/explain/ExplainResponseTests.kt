/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.indexmanagement.indexstatemanagement.transport.action.explain

import org.opensearch.common.io.stream.BytesStreamOutput
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.indexmanagement.indexstatemanagement.model.ManagedIndexMetaData
import org.opensearch.test.OpenSearchTestCase

class ExplainResponseTests : OpenSearchTestCase() {

    fun `test explain response`() {
        val indexNames = listOf("index1")
        val indexPolicyIDs = listOf("policyID1")
        val metadata = ManagedIndexMetaData(
            index = "index1",
            indexUuid = randomAlphaOfLength(10),
            policyID = "policyID1",
            policySeqNo = randomNonNegativeLong(),
            policyPrimaryTerm = randomNonNegativeLong(),
            policyCompleted = null,
            rolledOver = null,
            transitionTo = randomAlphaOfLength(10),
            stateMetaData = null,
            actionMetaData = null,
            stepMetaData = null,
            policyRetryInfo = null,
            info = null
        )
        val indexMetadatas = listOf(metadata)
        val res = ExplainResponse(indexNames, indexPolicyIDs, indexMetadatas)

        val out = BytesStreamOutput()
        res.writeTo(out)
        val sin = StreamInput.wrap(out.bytes().toBytesRef().bytes)
        val newRes = ExplainResponse(sin)
        assertEquals(indexNames, newRes.indexNames)
        assertEquals(indexPolicyIDs, newRes.indexPolicyIDs)
        assertEquals(indexMetadatas, newRes.indexMetadatas)
    }

    fun `test explain all response`() {
        val indexNames = listOf("index1")
        val indexPolicyIDs = listOf("policyID1")
        val metadata = ManagedIndexMetaData(
            index = "index1",
            indexUuid = randomAlphaOfLength(10),
            policyID = "policyID1",
            policySeqNo = randomNonNegativeLong(),
            policyPrimaryTerm = randomNonNegativeLong(),
            policyCompleted = null,
            rolledOver = null,
            transitionTo = randomAlphaOfLength(10),
            stateMetaData = null,
            actionMetaData = null,
            stepMetaData = null,
            policyRetryInfo = null,
            info = null
        )
        val indexMetadatas = listOf(metadata)
        val totalManagedIndices = 1
        val enabledState = mapOf("index1" to true)
        val res = ExplainAllResponse(indexNames, indexPolicyIDs, indexMetadatas, totalManagedIndices, enabledState)

        val out = BytesStreamOutput()
        res.writeTo(out)
        val sin = StreamInput.wrap(out.bytes().toBytesRef().bytes)
        val newRes = ExplainAllResponse(sin)
        assertEquals(indexNames, newRes.indexNames)
        assertEquals(indexPolicyIDs, newRes.indexPolicyIDs)
        assertEquals(indexMetadatas, newRes.indexMetadatas)
        assertEquals(totalManagedIndices, newRes.totalManagedIndices)
        assertEquals(enabledState, newRes.enabledState)
    }
}
