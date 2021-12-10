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

load("@rules_rust//rust:rust.bzl", "rust_library", "rust_binary")
load("@vaticle_dependencies//builder/rust:rules.bzl", "rust_cxx_bridge")

package(default_visibility = ["//query:__subpackages__"])

rust_library(
    name = "traversal",
    srcs = [
        "traversal.rs",
    ],
    deps = [
        "@vaticle_dependencies//library/crates:rocksdb",
        "@vaticle_dependencies//library/crates:cxx",
        "@vaticle_dependencies//library/ortools/rust:ortools",
    ]
)