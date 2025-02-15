/*
 * Copyright (C) 2022 Vaticle
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

package com.vaticle.typedb.core.concept.type.impl;

import com.vaticle.typedb.core.common.exception.TypeDBException;
import com.vaticle.typedb.core.common.iterator.sorted.SortedIterator.Forwardable;
import com.vaticle.typedb.core.common.parameters.Order;
import com.vaticle.typedb.core.common.util.StringBuilders;
import com.vaticle.typedb.core.concept.thing.Entity;
import com.vaticle.typedb.core.concept.thing.impl.EntityImpl;
import com.vaticle.typedb.core.concept.type.AttributeType;
import com.vaticle.typedb.core.concept.type.EntityType;
import com.vaticle.typedb.core.concept.type.RoleType;
import com.vaticle.typedb.core.concept.type.ThingType;
import com.vaticle.typedb.core.graph.GraphManager;
import com.vaticle.typedb.core.graph.vertex.ThingVertex;
import com.vaticle.typedb.core.graph.vertex.TypeVertex;

import java.util.List;

import static com.vaticle.typedb.core.common.exception.ErrorMessage.TypeRead.TYPE_ROOT_MISMATCH;
import static com.vaticle.typedb.core.common.exception.ErrorMessage.TypeWrite.ROOT_TYPE_MUTATION;
import static com.vaticle.typedb.core.common.iterator.sorted.SortedIterators.Forwardable.iterateSorted;
import static com.vaticle.typedb.core.common.parameters.Order.Asc.ASC;
import static com.vaticle.typedb.core.encoding.Encoding.Vertex.Type.ENTITY_TYPE;
import static com.vaticle.typedb.core.encoding.Encoding.Vertex.Type.Root.ENTITY;

public class EntityTypeImpl extends ThingTypeImpl implements EntityType {

    private EntityTypeImpl(GraphManager graphMgr, TypeVertex vertex) {
        super(graphMgr, vertex);
        if (vertex.encoding() != ENTITY_TYPE) {
            throw exception(TypeDBException.of(TYPE_ROOT_MISMATCH, vertex.label(),
                    ENTITY_TYPE.root().label(), vertex.encoding().root().label()));
        }
    }

    private EntityTypeImpl(GraphManager graphMgr, String label) {
        super(graphMgr, label, ENTITY_TYPE);
        assert !label.equals(ENTITY.label());
    }

    public static EntityTypeImpl of(GraphManager graphMgr, TypeVertex vertex) {
        if (vertex.label().equals(ENTITY.label())) {
            return new EntityTypeImpl.Root(graphMgr, vertex);
        } else return new EntityTypeImpl(graphMgr, vertex);
    }

    public static EntityTypeImpl of(GraphManager graphMgr, String label) {
        return new EntityTypeImpl(graphMgr, label);
    }

    @Override
    public void setSupertype(EntityType superType) {
        validateIsNotDeleted();
        setSuperTypeVertex(((EntityTypeImpl) superType).vertex);
    }

    @Override
    public Forwardable<EntityTypeImpl, Order.Asc> getSubtypes() {
        return iterateSorted(graphMgr.schema().getSubtypes(vertex), ASC)
                .mapSorted(v -> of(graphMgr, v), entityType -> entityType.vertex, ASC);
    }

    @Override
    public Forwardable<EntityTypeImpl, Order.Asc> getSubtypesExplicit() {
        return super.getSubtypesExplicit(v -> of(graphMgr, v));
    }

    @Override
    public Forwardable<EntityImpl, Order.Asc> getInstances() {
        return instances(EntityImpl::of);
    }

    @Override
    public Forwardable<EntityImpl, Order.Asc> getInstancesExplicit() {
        return instancesExplicit(EntityImpl::of);
    }

    @Override
    public List<TypeDBException> validate() {
        return super.validate();
    }

    @Override
    public EntityImpl create() {
        return create(false);
    }

    @Override
    public EntityImpl create(boolean isInferred) {
        validateCanHaveInstances(Entity.class);
        ThingVertex.Write instance = graphMgr.data().create(vertex, isInferred);
        return EntityImpl.of(instance);
    }

    @Override
    public boolean isEntityType() {
        return true;
    }

    @Override
    public EntityTypeImpl asEntityType() {
        return this;
    }

    @Override
    public void getSyntax(StringBuilder builder) {
        writeSupertype(builder);
        writeAbstract(builder);
        writeOwnsAttributes(builder);
        writePlays(builder);
        builder.append(StringBuilders.SEMICOLON_NEWLINE_X2);
    }

    private static class Root extends EntityTypeImpl {

        private Root(GraphManager graphMgr, TypeVertex vertex) {
            super(graphMgr, vertex);
            assert vertex.label().equals(ENTITY.label());
        }

        @Override
        public boolean isRoot() {
            return true;
        }

        @Override
        public void setLabel(String label) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void unsetAbstract() {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public Forwardable<ThingTypeImpl, Order.Asc> getSupertypes() {
            return iterateSorted(ASC, this);
        }

        @Override
        public void setSupertype(EntityType superType) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void setOwns(AttributeType attributeType, boolean isKey) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void setOwns(AttributeType attributeType, AttributeType overriddenType, boolean isKey) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void setPlays(RoleType roleType) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void setPlays(RoleType roleType, RoleType overriddenType) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }

        @Override
        public void unsetPlays(RoleType roleType) {
            throw exception(TypeDBException.of(ROOT_TYPE_MUTATION));
        }
    }
}
