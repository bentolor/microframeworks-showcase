/*
 *    Copyright 2015 Benjamin Schmid, @bentolor
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package bentolor.grocerylist;

import bentolor.grocerylist.persistence.ModelSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jodd.io.StreamUtil;
import jodd.madvoc.ActionRequest;
import jodd.madvoc.result.BaseActionResult;
import jodd.util.MimeTypes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JsonResultRenderer extends BaseActionResult<JsonResult> {
        private final ObjectMapper objectMapper
                = ModelSerializer.buildConfiguredObjectMapper();

        @Override
        public void render(ActionRequest request, JsonResult jsonResult) throws Exception {
            HttpServletResponse response = request.getHttpServletResponse();
            response.setContentType(MimeTypes.MIME_APPLICATION_JSON);

            if (jsonResult.getStatus() > 0) {
                response.setStatus(jsonResult.getStatus());
            }

            PrintWriter writer = null;
            try {
                writer = response.getWriter();
                String result;
                try {
                    result = objectMapper.writeValueAsString(jsonResult.getModel());
                } catch (JsonProcessingException e) {
                    throw new IllegalArgumentException("Invalid JSON to render");
                }
                writer.println(result);
            } finally {
                StreamUtil.close(writer);
            }
        }

}