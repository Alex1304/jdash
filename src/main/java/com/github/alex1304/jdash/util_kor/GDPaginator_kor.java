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
 * Represents a paginated list of GD entities. This list is itself implementing (GD 도면요소에 대한 표본을 표시한다. 이 목록은 그 자체로 도구이다.)
* {@link GDEntity}, and its ID corresponds to the ID of the first element, or 0
* if the list is empty. Note that all page numbers expressed in this class are (목록이 비어 있으면 이 클래스에 표시된 모든 페이지 번호는)
* zero-indexed. The first page is the page 0, and the last page is the total (제로 색인화.  번째 페이지는 0페이지, 마지막 페이지는 총계)
* number of pages - 1.
(페이지수 -1) *
 * @param <E> the type of GD entity contained in the list
(@param <E> 목록에 포함된 GD 엔티티 유형) */
public final class GDPaginator<E> implements Iterable<E>
{
private final List<E> list;
private final int pageNumber;
private final int maxSizePerPage;
private final int totalSize;
private final boolean hasNextPage;
private final boolean hasPreviousPage;
private final int totalNumberOfPages;
private final IntFunction<Mono<GDPaginator<E>>> pageLoader;
public GDPaginator(Collection<? extends E> collection, int page, int maxElementsPerPage, int totalElements,
IntFunction<Mono<GDPaginator<E>>> pageLoader)
{
this.list = Collections.unmodifiableList(new ArrayList<>(Objects.requireNonNull(collection)));
if (page < 0 || maxElementsPerPage < 0 || totalElements < 0)
{
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
 * Returns a List containing the same elements as this {@link GDPaginator}. (이 {@link GDPag네이터}과(와) 동일한 요소가 포함된 목록을 반환하십시오.) The
* returned List is read-only, meaning any modification attempted to be made on (반환된 목록은 읽기 전용이며, 이는 수정하려는 모든 것을 의미한다.)
* it will throw an {@link UnsupportedOperationException}. ({@link 지원되지 않는 운영이 발생함예외})
* 
 * @return an unmodifiable List
(@수정 불가능한 목록 반환) */
public List<E> asList()
{
return list;
}
/**
* Gets the current page number. (현재 페이지 번호를 가져오십시오)
 * * @return the page number
(@페이지 번호 반환) */
public int getPageNumber()
{
return pageNumber;
}

/**
 * Gets the maximum size that a single page can be.
(단일 페이지가 될 수 있는 최대 크기 가져오기)
 * 
 * @return the maximum size per page
(@페이지당 최대 크기 반환) */
public int getMaxSizePerPage()
{
return maxSizePerPage;
}

/**
 * Gets the total size. The total size is defined as the sum of the sizes of all (총 크기를 구한다. 총 크기는 모든 크기의 합으로 정의된다.)

 * pages. Note that for some reason, this may return zero if this is the last
(페이지. 어떤 이유로, 이것이 마지막일 경우 0이 반환될 수 있다는 점에 유의하십시오.)
 * page.
	
* 
 * @return the total size
(@전체 크기 반환) */
public int getTotalSize()
{
return totalSize;

}

/**
* Checks whether it has a next page. (다음 페이지가 있는지 확인한다.)
 * 
 * @return true if it has a next page, false if it's the last page
(@다음 페이지가 있으면 true, 마지막 페이지라면 false) */
public boolean hasNextPage()
{
return hasNextPage;
}

/**
 * Checks whether it has a previous page. (이전 페이지가 있는지 확인하십시오)
 * 
 * @return true if it has a previous page, false if the current page number is

 *     0.
(@이전 페이지가 있는 경우 true, 현재 페이지 번호가 0이면 false)*/
public boolean hasPreviousPage()
{
return hasPreviousPage;
}

/**
* Get the total number of pages.
(총 페이지 수를 구하십시오.)
 *  * @return the total number of pages
(@전체 페이지 수 반환) */
public int getTotalNumberOfPages()
{
return totalNumberOfPages + 1;
}
/**
 * Gets the number of elements contained in this page. (이 페이지에 포함된 요소의 수를 가져오십시오.)
*  * @return the page size; (@페이지 크기 반환) */
public int getPageSize()
{
return list.size();
}
/**  * Loads the next page.
(다음 페이지를 로드한다.)
 *  * @return a Mono emitting the next page, or IllegalStateException if already in

*  the last page
(@다음 페이지를 내보내는 모노 또는 마지막 페이지에 이미 있는 경우 불법 상태 예외 반환) */

public Mono<GDPaginator<E>> goToNextPage()
{
if (!hasNextPage)
{
return Mono.error(new IllegalStateException("There is no next page"));
}
return pageLoader.apply(pageNumber + 1);
}

/**
* Loads the previous page.
(이전 페이지 로드)
*  * @return a Mono emitting the previous page, or IllegalStateException if
(@이전 페이지를 내보내는 모노 또는 다음 경우 불법 상태 예외를 반환하십시오.)
 *  already in the first page
 */
public Mono<GDPaginator<E>> goToPreviousPage()
{
if (!hasPreviousPage)
{
return Mono.error(new IllegalStateException("There is no previous page"));
}
return pageLoader.apply(pageNumber - 1);
}

/**
 * Loads a specific page by providing its number. (번호를 제공하여 특정 페이지를 로드한다.)
*  * @param pageNumber the page number to load
(@param 페이지 로드할 페이지 번호)
* @return a Mono emitting the desired page, or IllegalArgumentException if
*  given page number is out of range
(원하는 페이지를 내보내는 모노 반환 또는 불법 인수지정된 페이지 번호가 범위를 벗어나는 경우 예외)	 */

public Mono<GDPaginator<E>> goTo(int pageNumber)
{
if (pageNumber > totalNumberOfPages || pageNumber < 0)
{
return Mono.error(new IllegalArgumentException("Page number out of range"));
}
return pageLoader.apply(pageNumber);
}
@Override
public Iterator<E> iterator()
{
return list.iterator();
}
public Stream<E> stream()
{
return list.stream();
}
@Override
public boolean equals(Object obj)
{
if (!(obj instanceof GDPaginator))
{
return false;
}
GDPaginator<?> p = (GDPaginator<?>) obj;
return p.list.equals(list) && p.pageNumber == pageNumber && p.maxSizePerPage == maxSizePerPage
&& p.totalSize == totalSize;
}@Override
public int hashCode()
{
return list.hashCode() ^ pageNumber ^ maxSizePerPage ^ totalSize;
	}

@Override

public String toString()
{
return String.format("GDPaginator [\n" + "\tPage %d of %d, showing %d elements out of %d\n"
+ "\tHas next page: %b ; Has previous page: %b\n" + "\tElements: %s\n" + "]",pageNumber + 1, totalNumberOfPages + 1, list.size(), totalSize, hasNextPage, hasPreviousPage,list.toString());
}
}
