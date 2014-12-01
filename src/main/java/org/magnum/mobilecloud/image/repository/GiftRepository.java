package org.magnum.mobilecloud.image.repository;

import java.util.Collection;

import org.magnum.mobilecloud.image.client.GiftSvcApi;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;



//i added
import java.util.List;
import java.util.Date;


/**
 * An interface for a repository that can store Gift
 * objects and allow them to be searched by title.
 * 
 * @author jules
 *
 */
// This @RepositoryRestResource annotation tells Spring Data Rest to
// expose the GiftRepository through a controller and map it to the 
// "/gift" path. This automatically enables you to do the following:
//
// 1. List all videos by sending a GET request to /video 
// 2. Add a video by sending a POST request to /video with the JSON for a video
// 3. Get a specific video by sending a GET request to /video/{videoId}
//    (e.g., /video/1 would return the JSON for the video with id=1)
// 4. Send search requests to our findByXYZ methods to /video/search/findByXYZ
//    (e.g., /video/search/findByName?title=Foo)
//

//@RepositoryRestResource(path = GiftSvcApi.GIFT_SVC_PATH)

public interface GiftRepository extends CrudRepository<Gift, Long>{

	// Find all videos with a matching title (e.g., Video.name)
	
	//public Collection<Gift> findByName(
			
			// The @Param annotation tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "title" variable used to
			// search for Videos
			
			//@Param(GiftSvcApi.TITLE_PARAMETER) String title);
	
	// Find all videos that are shorter than a specified duration
	
	//public Collection<Gift> findByDurationLessThan(
	
			// The @Param annotation tells tells Spring Data Rest which HTTP request
			// parameter it should use to fill in the "duration" variable used to
			// search for Videos
	
			//@Param(GiftSvcApi.DURATION_PARAMETER) long maxduration);
	
	/*
	 * See: http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html 
	 * for more examples of writing query methods
	 */
	
	//added this below
	/**
     * Given an ID, find the gift
     *
     * */

    Gift findById(long id);

    /**
     * Given a name, find the gift
     * */
    List<Gift> findByName(String name);

    List<Gift> findByCreationTime(Date creationTime);
}
