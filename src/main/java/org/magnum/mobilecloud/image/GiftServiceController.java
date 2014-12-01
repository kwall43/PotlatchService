package org.magnum.mobilecloud.image;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.magnum.mobilecloud.image.repository.Gift;
import org.magnum.mobilecloud.image.repository.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

@Controller
public class GiftServiceController {
	public static final String GIFT_PATH = "/gift";
	public static final String SEARCH_PATH = "/search";

	@Autowired
	private GiftRepository giftRepository;

	/**
	 * Returns the list of gifts that have been added to the server as JSON
	 * */
	@RequestMapping(value = GIFT_PATH, method = RequestMethod.GET)
	public @ResponseBody
	Collection<Gift> getGifts() {
		return Lists.newArrayList(giftRepository.findAll());
	}

	/**
	 * Return the gift with the given id or 404 if the gift is not found
	 * */
	@RequestMapping(value = GIFT_PATH + "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	Gift getGift(@PathVariable("id") long id, HttpServletResponse response) {
		Gift g = giftRepository.findById(id);
		if (g == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}

		return g;
	}

	/**
	 * Save the gift metadata provided by the client and returns the saved
	 * gift represented as JSON
	 * */
	@RequestMapping(value = GIFT_PATH, method = RequestMethod.POST)
	public @ResponseBody
	Gift addGiftMetadata(@RequestBody Gift gift) {
		return giftRepository.save(gift);
	}

	/**
	 * Allows a user to like a gift. Return 200 Ok on success, 404 if the gift
	 * is not found, or 400 if the user has already liked the gift.
	 * */
	@RequestMapping(value = GIFT_PATH + "/{id}/touched", method = RequestMethod.POST)
	public void touchedGift(@PathVariable("id") long id, Principal p,
			HttpServletResponse response) {
		Gift g = getGift(id, response);

		if (g != null) {
			boolean success = g.touchedGift(p.getName());
			if (success) {
				giftRepository.save(g);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}

	}

	/**
	 * Allows a user to unlike a gift. Return 200 Ok on success, 404 if the
	 * gift is not found, or 400 if the user has not previously liked the
	 * gift.
	 * */
	@RequestMapping(value = GIFT_PATH + "/{id}/untouched", method = RequestMethod.POST)
	public void unTouchedGift(@PathVariable("id") long id, Principal p,
			HttpServletResponse response) {
		Gift g = getGift(id, response);

		if (g != null) {
			boolean success = g.untouchedGift(p.getName());
			if (success) {
				giftRepository.save(g);
				response.setStatus(HttpServletResponse.SC_OK);
			} else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
	}

	/**
	 * Return a list of users that like the Gift, given the id of that gift
	 * */
	@RequestMapping(value = GIFT_PATH + "/{id}/touchedby", method = RequestMethod.GET)
	public @ResponseBody
	Set<String> getUserTouches(@PathVariable("id") long id,
			HttpServletResponse response) {
		Gift g = getGift(id, response);
		Set<String> userTouches = g.getUserTouches();

		if (g != null) {
			userTouches = g.getUserTouches();
			giftRepository.save(g);
		}

		return userTouches; 
	
		

	}

	/**
	 * Return a list of gifts, from the given title
	 * */
	@RequestMapping(value = GIFT_PATH + SEARCH_PATH + "/findByTitle", method = RequestMethod.GET)
	public @ResponseBody
	List<Gift> findGiftByTitle(@RequestParam("title") String title) {
		List<Gift> gifts = giftRepository.findByName(title);

		if (gifts == null) {
			gifts = Lists.newArrayList();
		}
		return gifts;
	}

	@RequestMapping(value = GIFT_PATH + "/{id}/unflagged", method = RequestMethod.POST)
    public void unflaggedGift(@PathVariable("id") long id, Principal p,
                              HttpServletResponse response) {
        Gift g = getGift(id, response);

        if (g != null) {
            boolean success = g.unflaggedGift(p.getName());
            if (success) {
                giftRepository.save(g);
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    /**
     * Return a list of users that were flagged by the Gift, given the id of that gift
     * */
    @RequestMapping(value = GIFT_PATH + "/{id}/flaggedby", method = RequestMethod.GET)
    public @ResponseBody
    Set<String> getUserFlags(@PathVariable("id") long id,
                               HttpServletResponse response) {
        Gift g = getGift(id, response);
        Set<String> userFlags = g.getUserFlagged();

        if (g != null) {
            userFlags = g.getUserFlagged();
            giftRepository.save(g);
        }

        return userFlags;
    }


}