// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import java.io.IOException;

final class MyJSONParser implements JSONParser {

    @Override
    public JSON parse(String in) throws IOException {
        MyJSON json = new MyJSON();

        if (isObject(in)) {
            int openBracketIndex = in.indexOf('{');

            String inner = in.substring(openBracketIndex + 1,
                    in.length() - 1).trim();

            if (inner.length() == 0) {
                return json;
            } else {
                int colonIndex = inner.indexOf(':');
                if (colonIndex == -1) {
                    throw new IOException("Invalid string.");
                }

                String name = inner.substring(0, colonIndex).trim();

                if (name.charAt(0) != '\"' || name.charAt(name.length() - 1)
                        != '\"') {
                    throw new IOException("Invalid string.");
                } else {
                    name = name.substring(1, name.length() - 1);

                    String value = inner.substring(colonIndex + 1).trim();

                    if (isObject(value)) {
                        json.setObject(name, parse(value));
                    } else {
                        String[] splitObjects = inner.split(",");
                        for (String pair : splitObjects) {
                            pair = pair.trim();
                            int colonPairIndex = pair.indexOf(':');
                            if (colonPairIndex == -1) {
                                throw new IOException("Invalid string.");
                            }

                            String pairName = pair.substring(0, colonPairIndex);
                            if (!isString(pairName)) {
                                throw new IOException("Invalid string.");
                            }

                            pairName = pairName.substring(1,
                                    pairName.length() - 1);
                            String pairValue = pair.substring(
                                    colonPairIndex + 1).trim();
                            if (!isString(pairValue)) {
                                throw new IOException("Invalid string.");
                            }

                            pairValue = pairValue.substring(1,
                                    pairValue.length() - 1);
                            json.setString(pairName, pairValue);
                        }
                    }
                }
            }
        } else if (isString(in)) {
            int colonIndex = in.indexOf(':');
            if (colonIndex == -1) {
                throw new IOException("Invalid string.");
            }

            String name = in.substring(0, colonIndex).trim();

            if (name.charAt(0) != '\"' || name.charAt(name.length() - 1)
                    != '\"') {
                throw new IOException("Invalid string.");
            } else {
                name = name.substring(1, name.length() - 1);
                int colonPairIndex = name.indexOf(':');
                String pairValue = name.substring(colonPairIndex + 1).trim();


                pairValue = pairValue.substring(1, pairValue.length() - 1);
                json.setString(name, pairValue);
            }
        } else {
            throw new IOException("Invalid string.");
        }
        return json;
    }

    /**
     * Determines if the String passed in is considered a JSON String
     *
     * @param value the value to be determined if is a String
     * @return if the value is a String or not
     */
    private boolean isString(String value) {
        if (value.charAt(0) == '\"' && value.charAt(value.length() - 1)
                == '\"') {
            return true;
        }
        return false;
    }

    /**
     * Determines if the String passed in is considered a JSON Object
     *
     * @param value the value to be determined if is an Object
     * @return if the value is an Object or not
     */
    private boolean isObject(String value) {
        if (value.charAt(0) == '{' && value.charAt(value.length() - 1) == '}') {
            return true;
        }
        return false;
    }
}
