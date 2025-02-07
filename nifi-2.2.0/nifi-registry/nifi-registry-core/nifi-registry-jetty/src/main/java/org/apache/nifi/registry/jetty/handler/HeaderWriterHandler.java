/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.registry.jetty.handler;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Handler;

import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

/**
 * HTTP Response Header Writer Handler applies standard headers to HTTP responses
 */
public class HeaderWriterHandler extends Handler.Wrapper {
    protected static final String CONTENT_SECURITY_POLICY_HEADER = "Content-Security-Policy";
    protected static final String CONTENT_SECURITY_POLICY = "frame-ancestors 'self'";

    protected static final String FRAME_OPTIONS_HEADER = "X-Frame-Options";
    protected static final String FRAME_OPTIONS = "SAMEORIGIN";

    protected static final String STRICT_TRANSPORT_SECURITY_HEADER = "Strict-Transport-Security";
    protected static final String STRICT_TRANSPORT_SECURITY = "max-age=31540000";

    protected static final String XSS_PROTECTION_HEADER = "X-XSS-Protection";
    protected static final String XSS_PROTECTION = "1; mode=block";

    /**
     * Handle requests and set HTTP response headers
     *
     * @param request Jetty Request
     * @param response Jetty Response
     * @param callback Jetty Callback
     * @return Handled status
     * @throws Exception Thrown on failures from subsequent handlers
     */
    @Override
    public boolean handle(final Request request, final Response response, final Callback callback) throws Exception {
        response.getHeaders().add(CONTENT_SECURITY_POLICY_HEADER, CONTENT_SECURITY_POLICY);
        response.getHeaders().add(FRAME_OPTIONS_HEADER, FRAME_OPTIONS);
        response.getHeaders().add(XSS_PROTECTION_HEADER, XSS_PROTECTION);

        if (request.isSecure()) {
            response.getHeaders().add(STRICT_TRANSPORT_SECURITY_HEADER, STRICT_TRANSPORT_SECURITY);
        }

        return super.handle(request, response, callback);
    }
}
