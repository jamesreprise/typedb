#
# Copyright (C) 2021 Vaticle
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

load("@vaticle_bazel_distribution//apt:rules.bzl", "assemble_apt", "deploy_apt")
load("@vaticle_bazel_distribution//artifact:rules.bzl", "deploy_artifact")
load("@vaticle_bazel_distribution//common:rules.bzl", "assemble_targz", "assemble_zip", "java_deps")
load("@vaticle_dependencies//tool/checkstyle:rules.bzl", "checkstyle_test")
load("@vaticle_dependencies//distribution:deployment.bzl", "deployment")
load("@vaticle_dependencies//builder/java:rules.bzl", "native_java_libraries")
load("@stackb_rules_proto//java:java_grpc_compile.bzl", "java_grpc_compile")


package(default_visibility = ["//visibility:private",])

assemble_files = {
    "//server/conf:typedb": "server/conf/typedb.yml",
    "//server/resources:logo": "server/resources/typedb-ascii.txt",
    "//:LICENSE": "LICENSE",
}

permissions = {
    "server/conf/typedb.yml" : "0755",
}

#assemble_targz(
#    name = "assemble-linux-targz",
#    targets = ["server-deps-linux", "@vaticle_typedb_common//binary:assemble-bash-targz"],
#    additional_files = assemble_files,
#    permissions = permissions,
#    output_filename = "typedb-server-linux",
#    visibility = ["//:__pkg__", "//test:__subpackages__"]
#)
#
#assemble_zip(
#    name = "assemble-mac-zip",
#    targets = assemble_deps_common + ["server-deps-mac", "@vaticle_typedb_common//binary:assemble-bash-targz"],
#    additional_files = assemble_files,
#    permissions = permissions,
#    output_filename = "typedb-server-mac",
#    visibility = ["//:__pkg__", "//test:__subpackages__"]
#)
#
#assemble_zip(
#    name = "assemble-windows-zip",
#    targets = ["server-deps-windows", "@vaticle_typedb_common//binary:assemble-bat-targz"],
#    additional_files = assemble_files,
#    permissions = permissions,
#    output_filename = "typedb-server-windows",
#    visibility = ["//:__pkg__", "//test:__subpackages__"]
#)
#
#deploy_artifact(
#    name = "deploy-linux-targz",
#    target = ":assemble-linux-targz",
#    artifact_group = "vaticle_typedb",
#    artifact_name = "typedb-server-linux-{version}.tar.gz",
#    release = deployment['artifact.release'],
#    snapshot = deployment['artifact.snapshot'],
#)
#
#deploy_artifact(
#    name = "deploy-mac-zip",
#    target = ":assemble-mac-zip",
#    artifact_group = "vaticle_typedb",
#    artifact_name = "typedb-server-mac-{version}.zip",
#    release = deployment['artifact.release'],
#    snapshot = deployment['artifact.snapshot'],
#)
#
#deploy_artifact(
#    name = "deploy-windows-zip",
#    target = ":assemble-windows-zip",
#    artifact_group = "vaticle_typedb",
#    artifact_name = "typedb-server-windows-{version}.zip",
#    release = deployment['artifact.release'],
#    snapshot = deployment['artifact.snapshot'],
#)
#
#assemble_apt(
#    name = "assemble-linux-apt",
#    package_name = "typedb-server",
#    maintainer = "Vaticle <community@vaticle.com>",
#    description = "TypeDB (server)",
#    depends = [
#      "openjdk-11-jre",
#      "typedb-bin (=%{@vaticle_typedb_common})"
#    ],
#    workspace_refs = "@vaticle_typedb_workspace_refs//:refs.json",
#    archives = [":server-deps-linux"],
#    installation_dir = "/opt/typedb/core/",
#    files = assemble_files,
#    empty_dirs = [
#        "opt/typedb/core/server/lib/dev",
#        "opt/typedb/core/server/lib/prod",
#        "opt/typedb/core/server/lib/common",
#        "var/lib/typedb/core/data/"
#     ],
#    permissions = {
#        "var/lib/typedb/core/data/": "0777",
#    },
#    symlinks = {
#        "opt/typedb/core/server/data": "/var/lib/typedb/core/data/",
#    },
#)
#
#deploy_apt(
#    name = "deploy-apt",
#    target = ":assemble-linux-apt",
#    snapshot = deployment['apt.snapshot'],
#    release = deployment['apt.release']
#)

checkstyle_test(
    name = "checkstyle",
    include = glob(["*", "*/*", "*/*/*"]),
    license_type = "agpl",
)

