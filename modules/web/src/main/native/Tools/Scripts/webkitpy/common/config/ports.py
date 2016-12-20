# Copyright (C) 2009, Google Inc. All rights reserved.
# Copyright (C) 2013 Nokia Corporation and/or its subsidiary(-ies).
# Copyright (C) 2015 Apple Inc. All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are
# met:
#
#     * Redistributions of source code must retain the above copyright
# notice, this list of conditions and the following disclaimer.
#     * Redistributions in binary form must reproduce the above
# copyright notice, this list of conditions and the following disclaimer
# in the documentation and/or other materials provided with the
# distribution.
#     * Neither the name of Google Inc. nor the names of its
# contributors may be used to endorse or promote products derived from
# this software without specific prior written permission.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
# "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
# LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
# A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
# OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
# LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
# DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
# THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
# OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# WebKit's Python module for understanding the various ports

import os
import platform
import sys

from webkitpy.common.system.executive import Executive


class DeprecatedPort(object):
    results_directory = "/tmp/layout-test-results"

    # Subclasses must override
    port_flag_name = None

    # FIXME: This is only used by BotInfo.
    def name(self):
        return self.__class__

    def flag(self):
        if self.port_flag_name:
            return "--port=%s" % self.port_flag_name
        return None

    # We might need to pass scm into this function for scm.checkout_root
    def script_path(self, script_name):
        return os.path.join("Tools", "Scripts", script_name)

    def script_shell_command(self, script_name):
        script_path = self.script_path(script_name)
        return Executive.shell_command_for_script(script_path)

    @staticmethod
    def port(port_name):
        ports = {
            "gtk-wk2": GtkWK2Port,
            "ios": IOSPort,
            "mac": MacPort,
            "mac-wk2": MacWK2Port,
            "win": WinPort,
            "wincairo": WinCairoPort,
            "efl-wk2": EflWK2Port,
        }
        default_port = {
            "Windows": WinPort,
            "Darwin": MacPort,
        }
        # Do we really need MacPort as the ultimate default?
        return ports.get(port_name, default_port.get(platform.system(), MacPort))()

    def makeArgs(self):
        # FIXME: This shouldn't use a static Executive().
        args = '--makeargs="-j%s"' % Executive().cpu_count()
        if os.environ.has_key('MAKEFLAGS'):
            args = '--makeargs="%s"' % os.environ['MAKEFLAGS']
        return args

    def update_webkit_command(self, non_interactive=False):
        return self.script_shell_command("update-webkit")

    def check_webkit_style_command(self):
        return self.script_shell_command("check-webkit-style")

    def prepare_changelog_command(self):
        return self.script_shell_command("prepare-ChangeLog")

    def build_webkit_command(self, build_style=None):
        command = self.script_shell_command("build-webkit")
        if build_style == "debug":
            command.append("--debug")
        if build_style == "release":
            command.append("--release")
        return command

    def run_javascriptcore_tests_command(self):
        return self.script_shell_command("run-javascriptcore-tests")

    def run_webkit_tests_command(self, build_style=None):
        command = self.script_shell_command("run-webkit-tests")
        if build_style == "debug":
            command.append("--debug")
        if build_style == "release":
            command.append("--release")
        return command

    def run_python_unittests_command(self):
        return self.script_shell_command("test-webkitpy")

    def run_perl_unittests_command(self):
        return self.script_shell_command("test-webkitperl")

    def run_bindings_tests_command(self):
        return self.script_shell_command("run-bindings-tests")


class IOSPort(DeprecatedPort):
    port_flag_name = "ios"

    def build_webkit_command(self, build_style=None):
        command = super(IOSPort, self).build_webkit_command(build_style=build_style)
        command.append("--sdk=iphoneos")
        return command


class MacPort(DeprecatedPort):
    port_flag_name = "mac"

    def run_webkit_tests_command(self, build_style=None):
        command = super(MacPort, self).run_webkit_tests_command(build_style)
        command.append("--dump-render-tree")
        return command

class MacWK2Port(DeprecatedPort):
    port_flag_name = "mac-wk2"


class WinPort(DeprecatedPort):
    port_flag_name = "win"

    def run_bindings_tests_command(self):
        return None

    def run_webkit_tests_command(self, build_style=None):
        command = super(WinPort, self).run_webkit_tests_command(build_style)
        command.append("--dump-render-tree")
        return command


class WinCairoPort(DeprecatedPort):
    port_flag_name = "wincairo"


class GtkWK2Port(DeprecatedPort):
    port_flag_name = "gtk-wk2"

    def build_webkit_command(self, build_style=None):
        command = super(GtkWK2Port, self).build_webkit_command(build_style=build_style)
        command.append("--gtk")
        command.append("--update-gtk")
        command.append(super(GtkWK2Port, self).makeArgs())
        return command

    def run_webkit_tests_command(self, build_style=None):
        command = super(GtkWK2Port, self).run_webkit_tests_command(build_style)
        command.append("--gtk")
        return command


class EflWK2Port(DeprecatedPort):
    port_flag_name = "efl-wk2"

    def build_webkit_command(self, build_style=None):
        command = super(EflWK2Port, self).build_webkit_command(build_style=build_style)
        command.append("--efl")
        command.append("--update-efl")
        command.append(super(EflWK2Port, self).makeArgs())
        return command
