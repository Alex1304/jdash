package com.github.alex1304.jdash.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import com.github.alex1304.jdash.entity.GDEntity;

import reactor.core.publisher.Mono;

/**
 * Represents a paginated list of GD entities. This list is itself implementing
 * {@link GDEntity}, and its ID corresponds to the ID of the first element, or 0
 * if the list is empty. Note that all page numbers expressed in this class are
 * zero-indexed. The first page is the page 0, and the last page is the total
 * number of pages - 1.
 *
 * @param <E> the type of GD entity contained in the list
 */
public class GDPaginator<E> implements Iterable<E> {

	private final List<E> list;
	private final int pageNumber;
	private final int maxSizePerPage;
	private final int totalSize;
	private final boolean hasNextPage;
	private final boolean hasPreviousPage;
	private final int totalNumberOfPages;
	private final IntFunction<Mono<GDPaginator<E>>> pageLoader;

	public GDPaginator(Collection<? extends E> collection, int page, int maxElementsPerPage, int totalElements,
			IntFunction<Mono<GDPaginator<E>>> pageLoader) {
		this.list = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(collection)));
		if (page < 0 || maxElementsPerPage < 0 || totalElements < 0) {
			throw new IllegalArgumentException("Negative value given");
		}
		this.pageNumber = page;
		this.maxSizePerPage = maxElementsPerPage;
		this.totalSize = totalElements;
		this.totalNumberOfPages = totalElements / maxElementsPerPage;
		this.hasNextPage = page < totalNumberOfPages;
		this.hasPreviousPage = page > 0;
		this.pageLoader = Objects.requireNonNull(pageLoader);
	}

	/**
	 * Returns a List containing the same elements as this {@link GDPaginator}. The
	 * returned List is read-only, meaning any modification attempted to be made on
	 * it will throw an {@link UnsupportedOperationException}.
	 * 
	 * @return an unmodifiable List
	 */
	public List<E> asList() {
		return list;
	}

	/**
	 * Gets the current page number.
	 * 
	 * @return the page number
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * Gets the maximum size that a single page can be.
	 * 
	 * @return the maximum size per page
	 */
	public int getMaxSizePerPage() {
		return maxSizePerPage;
	}

	/**
	 * Gets the total size. The total size is defined as the sum of the sizes of all
	 * pages.
	 * 
	 * @return the total size
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * Checks whether it has a next page.
	 * 
	 * @return true if it has a next page, false if it's the last page
	 */
	public boolean hasNextPage() {
		return hasNextPage;
	}

	/**
	 * Checks whether it has a previous page.
	 * 
	 * @return true if it has a previous page, false if the current page number is
	 *         0.
	 */
	public boolean hasPreviousPage() {
		return hasPreviousPage;
	}

	/**
	 * Get the total number of pages.
	 * 
	 * @return the total number of pages
	 */
	public int getTotalNumberOfPages() {
		return totalNumberOfPages + 1;
	}

	/**
	 * Gets the number of elements contained in this page.
	 * 
	 * @return the page size;
	 */
	public int getPageSize() {
		return list.size();
	}

	/**
	 * Loads the next page.
	 * 
	 * @return a Mono emitting the next page
	 * @throws IllegalStateException if already in the last page
	 */
	public Mono<GDPaginator<E>> goToNextPage() {
		if (!hasNextPage) {
			throw new IllegalStateException("There is no next page");
		}
		return pageLoader.apply(pageNumber + 1);
	}

	/**
	 * Loads the previous page.
	 * 
	 * @return a Mono emitting the previous page
	 * @throws IllegalStateException if already in the first page
	 */
	public Mono<GDPaginator<E>> goToPreviousPage() {
		if (!hasPreviousPage) {
			throw new IllegalStateException("There is no previous page");
		}
		return pageLoader.apply(pageNumber - 1);
	}

	/**
	 * Loads a specific page by providing its number.
	 * 
	 * @param page the page number to load
	 * @return a Mono emitting the desired page
	 * @throws IllegalArgumentException if the given page number is out of range
	 */
	public Mono<GDPaginator<E>> goTo(int pageNumber) {
		if (pageNumber > totalNumberOfPages || pageNumber < 0) {
			throw new IllegalArgumentException("Page number out of range");
		}
		return pageLoader.apply(pageNumber);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	public Stream<E> stream() {
		return list.stream();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof GDPaginator)) {
			return false;
		}
		GDPaginator<?> p = (GDPaginator<?>) obj;
		return p.list.equals(list) && p.pageNumber == pageNumber && p.maxSizePerPage == maxSizePerPage
				&& p.totalSize == totalSize;
	}
	
	@Override
	public int hashCode() {
		return list.hashCode() ^ pageNumber ^ maxSizePerPage ^ totalSize;
	}

	@Override
	public String toString() {
		return String.format("GDPaginator [\n"
				+ "\tPage %d of %d, showing %d elements out of %d\n"
				+ "\tHas next page: %b ; Has previous page: %b\n"
				+ "\tElements: %s\n"
				+ "]", pageNumber + 1, totalNumberOfPages + 1, list.size(), totalSize,
				hasNextPage, hasPreviousPage, list.toString());
	}
}
