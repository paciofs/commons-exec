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

package org.apache.commons.exec.launcher;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.CommandLineImpl;
import org.apache.commons.exec.environment.Environment;

/**
 * A command launcher for OS/2 that uses 'cmd.exe' when launching commands in
 * directories other than the current working directory.
 * <p>
 * Unlike Windows NT and friends, OS/2's cd doesn't support the /d switch to
 * change drives and directories in one go.
 * </p>
 */
public class OS2CommandLauncher extends CommandLauncherProxy {

    public OS2CommandLauncher(final CommandLauncher launcher) {
        super(launcher);
    }

    /**
     * Launches the given command in a new process, in the given working
     * directory.
     * 
     * @param project
     *            the ant project
     * @param cmd
     *            the command line to execute as an array of strings
     * @param env
     *            the environment to set as an array of strings
     * @param workingDir
     *            working directory where the command should run
     * @throws IOException
     *             forwarded from the exec method of the command launcher
     */
    public Process exec(final CommandLine cmd, final Environment env,
            final File workingDir) throws IOException {
        if (workingDir == null) {
            return exec(cmd, env);
        }

        CommandLine newCmd = new CommandLineImpl();
        newCmd.setExecutable("cmd");
        newCmd.addArgument("/c");
        newCmd.addArguments(cmd.getCommandline());

        return exec(newCmd, env);
    }
}
