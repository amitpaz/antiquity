/**
 * Copyright (c) 2012-2013 "Vertix Technologies, ltd."
 *
 * This file is part of Antiquity.
 *
 * Antiquity is free software: you can redistribute it and/or modify
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.vertixtech.antiquity.graph;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.tinkerpop.blueprints.Edge;

/**
 * An implementation of {@link Predicate} for filtering edges that are not contained within a specified version range.
 * 
 * @see VersionedEdgeIterable
 * @param <V>
 *            The graph identifier type
 */
public class VersionedVertexEdgePredicate<V extends Comparable<V>> implements Predicate<Edge> {
	Logger log = LoggerFactory.getLogger(VersionedVertexEdgePredicate.class);
	private final V version;
	private final VersionedGraph<?, V> graph;

	/**
	 * Create an instance of this class.
	 * 
	 * @param graph
	 *            The {@link VersionedGraph} instance associated with this predicate
	 * @param version
	 *            The version of the predicate
	 */
	public VersionedVertexEdgePredicate(VersionedGraph<?, V> graph, V version) {
		this.version = version;
		this.graph = graph;
	}

	@Override
	public boolean apply(Edge edge) {
		// Apply filtering only for versioned edges
		// TODO: Create a better approach for finding versioned edges
		if (!edge.getPropertyKeys().contains(VersionedGraph.VALID_MIN_VERSION_PROP_KEY))
			return true;

		boolean isEdgeInRange = graph.getVersionRange(edge).contains(version);
		log.debug("Is edge[{}] is valid for version [{}] ? {}", edge, version, isEdgeInRange);

		return isEdgeInRange;
	}
}