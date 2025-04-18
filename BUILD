# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.

load("@com_github_grpc_grpc//bazel:cython_library.bzl", "pyx_library")
load("@compile_commands_extractor//:refresh_compile_commands.bzl", "refresh_compile_commands")


pyx_library(
    name = "_util",
    srcs = glob([
        "python/pyfury/includes/*.pxd",
        "python/pyfury/_util.pxd",
        "python/pyfury/_util.pyx",
        "python/pyfury/__init__.py",
    ]),
    cc_kwargs = dict(
        linkstatic = 1,
    ),
    deps = [
        "//cpp/fury/util:fury_util",
    ],
)

pyx_library(
    name = "mmh3",
    srcs = glob([
        "python/pyfury/lib/mmh3/*.pxd",
        "python/pyfury/lib/mmh3/*.pyx",
        "python/pyfury/lib/mmh3/__init__.py",
    ]),
    cc_kwargs = dict(
        linkstatic = 1,
    ),
    deps = [
        "//cpp/fury/thirdparty:libmmh3",
    ],
)

pyx_library(
    name = "_serialization",
    srcs = glob([
        "python/pyfury/includes/*.pxd",
        "python/pyfury/_util.pxd",
        "python/pyfury/_serialization.pyx",
        "python/pyfury/__init__.py",
    ]),
    cc_kwargs = dict(
        linkstatic = 1,
    ),
    deps = [
        "//cpp/fury/util:fury_util",
        "//cpp/fury/type:fury_type",
        "//cpp/fury/python:_pyfury",
        "@com_google_absl//absl/container:flat_hash_map",
    ],
)

pyx_library(
    name = "_format",
    srcs = glob([
        "python/pyfury/__init__.py",
        "python/pyfury/includes/*.pxd",
        "python/pyfury/_util.pxd",
        "python/pyfury/*.pxi",
        "python/pyfury/format/_format.pyx",
        "python/pyfury/format/__init__.py",
        "python/pyfury/format/*.pxi",
    ]),
    cc_kwargs = dict(
        linkstatic = 1,
    ),
    deps = [
        "//cpp/fury:fury",
        "@local_config_pyarrow//:python_numpy_headers",
        "@local_config_pyarrow//:arrow_python_shared_library"
    ],
)

genrule(
    name = "cp_fury_so",
    srcs = [
        ":python/pyfury/_util.so",
        ":python/pyfury/lib/mmh3/mmh3.so",
        ":python/pyfury/format/_format.so",
        ":python/pyfury/_serialization.so",
    ],
    outs = [
        "cp_fury_py_generated.out",
    ],
    cmd = """
        set -e
        set -x
        WORK_DIR=$$(pwd)
        u_name=`uname -s`
        if [ "$${u_name: 0: 4}" == "MING" ] || [ "$${u_name: 0: 4}" == "MSYS" ]
        then
            cp -f $(location python/pyfury/_util.so) "$$WORK_DIR/python/pyfury/_util.pyd"
            cp -f $(location python/pyfury/lib/mmh3/mmh3.so) "$$WORK_DIR/python/pyfury/lib/mmh3/mmh3.pyd"
            cp -f $(location python/pyfury/format/_format.so) "$$WORK_DIR/python/pyfury/format/_format.pyd"
            cp -f $(location python/pyfury/_serialization.so) "$$WORK_DIR/python/pyfury/_serialization.pyd"
        else
            cp -f $(location python/pyfury/_util.so) "$$WORK_DIR/python/pyfury"
            cp -f $(location python/pyfury/lib/mmh3/mmh3.so) "$$WORK_DIR/python/pyfury/lib/mmh3"
            cp -f $(location python/pyfury/format/_format.so) "$$WORK_DIR/python/pyfury/format"
            cp -f $(location python/pyfury/_serialization.so) "$$WORK_DIR/python/pyfury"
        fi
        echo $$(date) > $@
    """,
    local = 1,
    tags = ["no-cache"],
    visibility = ["//visibility:public"],
)

refresh_compile_commands(
    name = "refresh_compile_commands",
    exclude_headers = "all",
    exclude_external_sources = True,
)