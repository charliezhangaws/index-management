/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * The OpenSearch Contributors require contributions made to
 * this file be licensed under the Apache-2.0 license or a
 * compatible open source license.
 *
 * Modifications Copyright OpenSearch Contributors. See
 * GitHub history for details.
 */

/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.opensearch.indexmanagement.indexstatemanagement.transport.action.retryfailedmanagedindex

import org.opensearch.action.ActionRequest
import org.opensearch.action.ActionRequestValidationException
import org.opensearch.action.ValidateActions
import org.opensearch.common.io.stream.StreamInput
import org.opensearch.common.io.stream.StreamOutput
import org.opensearch.common.unit.TimeValue
import java.io.IOException

class RetryFailedManagedIndexRequest : ActionRequest {

    val indices: List<String>
    val startState: String?
    val masterTimeout: TimeValue

    constructor(
        indices: List<String>,
        startState: String?,
        masterTimeout: TimeValue
    ) : super() {
        this.indices = indices
        this.startState = startState
        this.masterTimeout = masterTimeout
    }

    @Throws(IOException::class)
    constructor(sin: StreamInput) : this(
        indices = sin.readStringList(),
        startState = sin.readOptionalString(),
        masterTimeout = sin.readTimeValue()
    )

    override fun validate(): ActionRequestValidationException? {
        var validationException: ActionRequestValidationException? = null
        if (indices.isEmpty()) {
            validationException = ValidateActions.addValidationError("Missing indices", validationException)
        }
        return validationException
    }

    @Throws(IOException::class)
    override fun writeTo(out: StreamOutput) {
        out.writeStringCollection(indices)
        out.writeOptionalString(startState)
        out.writeTimeValue(masterTimeout)
    }
}