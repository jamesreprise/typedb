/*
 * Copyright (C) 2020 Grakn Labs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package hypergraph.graph.edge;

import hypergraph.graph.Schema;
import hypergraph.graph.Storage;
import hypergraph.graph.vertex.TypeVertex;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static hypergraph.graph.util.ByteArrays.join;

public abstract class TypeEdge extends Edge<Schema.Edge.Type, TypeVertex> {

    TypeEdge(Schema.Edge.Type schema, TypeVertex from, TypeVertex to) {
        super(schema, from, to);
    }

    public static class Buffered extends TypeEdge {

        private Storage storage;
        private AtomicBoolean committed;

        public Buffered(Storage storage, Schema.Edge.Type schema, TypeVertex from, TypeVertex to) {
            super(schema, from, to);
            this.storage = storage;
            committed = new AtomicBoolean(false);
        }

        public Schema.Status status() {
            return committed.get() ? Schema.Status.COMMITTED : Schema.Status.BUFFERED;
        }

        public void commit() {
            if (committed.compareAndSet(false, true)) {
                if (schema.out() != null) {
                    storage.put(join(from.iid(), schema.out().key(), to.iid()));
                }
                if (schema.in() != null) {
                    storage.put(join(to.iid(), schema.in().key(), from.iid()));
                }
            }
        }
    }

    public static class Persisted extends TypeEdge {

        public Persisted(Storage storage, byte[] iid) {
            super(
                    Schema.Edge.Type.of(iid[Schema.IID.TYPE.length()]),
                    new TypeVertex.Persisted(storage, Arrays.copyOfRange(iid, 0, Schema.IID.TYPE.length())),
                    new TypeVertex.Persisted(storage, Arrays.copyOfRange(iid, Schema.IID.TYPE.length() + 1, iid.length))
            );
        }

        public Schema.Status status() {
            return Schema.Status.PERSISTED;
        }

        public void commit() {}
    }
}
