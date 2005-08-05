/* 
 * Copyright 2005  The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package org.apache.commons.exec;

import java.io.ByteArrayOutputStream;

import junit.framework.TestCase;

import org.apache.commons.exec.environment.Environment;

public class ExecTest extends TestCase {

    private String testDir = "src/test/scripts";
    private ByteArrayOutputStream baos;
    private String testScript = TestUtil.resolveScriptForOS(testDir + "/test");

    protected void setUp() throws Exception {
        baos = new ByteArrayOutputStream();
    }

    public void testExecute() throws Exception {
        Exec exec = new Exec();

        CommandLine cl = new CommandLineImpl();
        cl.setExecutable(testScript);

        exec.execute(cl, baos, baos);

        assertEquals("FOO", baos.toString().trim());
    }

    public void testExecuteWithArg() throws Exception {
        Exec exec = new Exec();

        CommandLine cl = new CommandLineImpl();
        cl.setExecutable(testScript);
        cl.addArgument("BAR");
        exec.execute(cl, baos, baos);

        assertEquals("FOO  BAR", baos.toString().trim());
    }

    public void testExecuteWithEnv() throws Exception {
        Environment env = Environment.createEnvironment();
        env.addVariable("TEST_ENV_VAR", "XYZ");

        CommandLine cl = new CommandLineImpl();
        cl.setExecutable(testScript);

        Exec exec = new Exec();

        exec.execute(cl, env, baos, baos);

        assertEquals("FOO XYZ", baos.toString().trim());
    }

    protected void tearDown() throws Exception {
        baos.close();
    }
}
