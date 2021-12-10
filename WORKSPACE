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

workspace(name = "vaticle_typedb")

################################
# Load @vaticle_dependencies #
################################

load("//dependencies/vaticle:repositories.bzl", "vaticle_dependencies")
vaticle_dependencies()

# Load //builder/bazel for RBE
load("@vaticle_dependencies//builder/bazel:deps.bzl", "bazel_toolchain")
bazel_toolchain()

# Load //build/rust
load("@vaticle_dependencies//builder/rust:deps.bzl", rust_deps = "deps")
rust_deps()
load("@rules_rust//rust:repositories.bzl", "rust_repositories")
rust_repositories(version = "nightly", iso_date = "2021-07-01", edition="2018")

# Load //builder/java
load("@vaticle_dependencies//builder/java:deps.bzl", java_deps = "deps")
java_deps()

# Load //builder/antlr
load("@vaticle_dependencies//builder/antlr:deps.bzl", antlr_deps = "deps", "antlr_version")
antlr_deps()

load("@rules_antlr//antlr:lang.bzl", "RUST")
load("@rules_antlr//antlr:repositories.bzl", "rules_antlr_dependencies")
rules_antlr_dependencies(antlr_version, RUST)

# Load //builder/kotlin
load("@vaticle_dependencies//builder/kotlin:deps.bzl", kotlin_deps = "deps")
kotlin_deps()
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories()
kt_register_toolchains()

# Load //builder/grpc
load("@vaticle_dependencies//builder/grpc:deps.bzl", grpc_deps = "deps")
grpc_deps()
load("@com_github_grpc_grpc//bazel:grpc_deps.bzl",
com_github_grpc_grpc_deps = "grpc_deps")
com_github_grpc_grpc_deps()

# Load //builder/python
load("@vaticle_dependencies//builder/python:deps.bzl", python_deps = "deps")
python_deps()

# Load //tool/common
load("@vaticle_dependencies//tool/common:deps.bzl", "vaticle_dependencies_ci_pip", "maven_artifacts")
load("@vaticle_dependencies//library/maven:rules.bzl", "maven")
vaticle_dependencies_ci_pip()
maven(maven_artifacts)

# Load //tool/checkstyle
load("@vaticle_dependencies//tool/checkstyle:deps.bzl", checkstyle_deps = "deps")
checkstyle_deps()

# Load //distribution/docker
load("@vaticle_dependencies//distribution/docker:deps.bzl", docker_deps = "deps")
docker_deps()

load("@io_bazel_rules_docker//repositories:repositories.bzl", bazel_rules_docker_repositories = "repositories")
bazel_rules_docker_repositories()

load("@io_bazel_rules_docker//repositories:deps.bzl", bazel_rules_docker_container_deps = "deps")
bazel_rules_docker_container_deps()

load("@io_bazel_rules_docker//container:container.bzl", "container_pull")
container_pull(
  name = "vaticle_ubuntu_image",
  registry = "index.docker.io",
  repository = "vaticle/ubuntu",
  tag = "4ee548cea883c716055566847c4736a7ef791c38"
)

######################################
# Load @vaticle_bazel_distribution #
######################################

load("@vaticle_dependencies//distribution:deps.bzl", "vaticle_bazel_distribution")
vaticle_bazel_distribution()

# Load //common
load("@vaticle_bazel_distribution//common:deps.bzl", "rules_pkg")
rules_pkg()
load("@rules_pkg//:deps.bzl", "rules_pkg_dependencies")
rules_pkg_dependencies()

# Load //github
load("@vaticle_bazel_distribution//github:deps.bzl", github_deps = "deps")
github_deps()

# Load //pip
load("@vaticle_bazel_distribution//pip:deps.bzl", pip_deps = "deps")
pip_deps()

#####################################
# Load @vaticle/typedb dependencies #
#####################################

# We don't load Maven artifacts for @vaticle_typedb_common as they are only needed
# if you depend on @vaticle_typedb_common//test/server
load(
    "//dependencies/vaticle:repositories.bzl",
    "vaticle_typedb_common", "vaticle_typeql_lang_rust",
    "vaticle_typedb_protocol", "vaticle_typedb_behaviour"
)
vaticle_typedb_common()
vaticle_typedb_protocol()
vaticle_typedb_behaviour()
vaticle_typeql_lang_rust()

load("@vaticle_typeql_lang_rust//dependencies/vaticle:repositories.bzl", "vaticle_typeql")
vaticle_typeql()

load("//dependencies/vaticle:artifacts.bzl", "vaticle_typedb_console_artifact")
vaticle_typedb_console_artifact()

load(
    "@vaticle_dependencies//library/ortools/cc:deps.bzl",
    "google_or_tools_linux", "google_or_tools_mac", "google_or_tools_windows"
)
google_or_tools_linux()
google_or_tools_mac()
google_or_tools_windows()

################################
# Load Rust Crate dependencies #
################################

load("@vaticle_dependencies//library/crates:crates.bzl", "raze_fetch_remote_crates")
raze_fetch_remote_crates()

###############################################
# Create @vaticle_typedb_workspace_refs #
###############################################

load("@vaticle_bazel_distribution//common:rules.bzl", "workspace_refs")
workspace_refs(name = "vaticle_typedb_workspace_refs")

