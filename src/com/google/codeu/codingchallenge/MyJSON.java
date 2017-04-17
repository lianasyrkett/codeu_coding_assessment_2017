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

import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

final class MyJSON implements JSON {

    private Map<String, Object> myValues = new HashMap();

    @Override
    public JSON getObject(String name) {
        Object returnValue = myValues.get(name);
        if (returnValue instanceof JSON) {
            return (JSON) returnValue;
        }
        return null;
    }

    @Override
    public JSON setObject(String name, JSON value) {
        myValues.put(name, value);
        return this;
    }

    @Override
    public String getString(String name) {
        Object returnValue = myValues.get(name);
        if (returnValue instanceof String) {
            return (String) returnValue;
        }
        return null;
    }

    @Override
    public JSON setString(String name, String value) {
        myValues.put(name, value);
        return this;
    }

    @Override
    public void getObjects(Collection<String> names) {
        Set<String> keys = myValues.keySet();
        for (String key : keys) {
            if (myValues.get(key) instanceof JSON) {
                names.add(key);
            }
        }
    }

    @Override
    public void getStrings(Collection<String> names) {
        Set<String> keys = myValues.keySet();
        for (String key : keys) {
            if (myValues.get(key) instanceof String) {
                names.add(key);
            }
        }
    }
}

