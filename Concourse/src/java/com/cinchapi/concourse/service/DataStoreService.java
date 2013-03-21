/*
 * This project is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This project is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this project. If not, see <http://www.gnu.org/licenses/>.
 */
package com.cinchapi.concourse.service;

/**
 * Provides {@link QueryService}, {@link ReadService} and {@link WriteService}
 * interfaces to a datastore.
 * 
 * @author jnelson
 */
public interface DataStoreService extends
		QueryService,
		ReadService,
		WriteService {

	/**
	 * Shutdown the service.
	 */
	public abstract void shutdown();

	/**
	 * Return the size of all the data stored in service.
	 * 
	 * @return the total size.
	 */
	public long sizeOf();

	/**
	 * Return the size in {@code row}
	 * 
	 * @param row
	 * @return the size of the data stored in all the cells in {@code row}.
	 */
	public long sizeOf(long row);

	/**
	 * Return the size of {@code column} in {@code row}.
	 * 
	 * @param column
	 * @param row
	 * 
	 * @return the size of the data stored in the cell at {@code row}:
	 *         {@code column}.
	 */
	public long sizeOf(String column, Long row);
}
