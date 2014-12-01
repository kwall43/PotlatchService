package org.magnum.mobilecloud.image.repository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.common.base.Objects;

//i added
import java.lang.String;
import java.util.Set;
import java.util.Date;
import javax.persistence.ElementCollection;

//added from asgn 1 model
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fluentinterface.ReflectionBuilder;
import com.fluentinterface.builder.Builder;


/**
 * A simple object to represent a video and its URL for viewing.
 * 
 * @author jules
 * 
 */
@Entity
public class Gift {
	
	public static GiftBuilder create(){
    return ReflectionBuilder.implementationFor(GiftBuilder.class).create();
	}

	public interface GiftBuilder extends Builder<Gift> {
    public GiftBuilder withTitle(String title);
    public GiftBuilder withText(String text);
    public GiftBuilder withContentType(String contentType);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	//private String name;
    private String url;
    private long touches;
    private long flagged;
    private Date creationTime;
    private String title;
    private String text;
    private String contentType;
    @JsonIgnore
    private String dataUrl;
    private String giftCreatorId;
    private String giftChainId;
    
    @ElementCollection
    private Set<String> userTouches;

    @ElementCollection
    private Set<String> userFlagged;

    public Gift() {
	}

    public Gift( long id, String dataUrl, long touches, long flagged, Date creationTime, String title, String text, String contentType, String giftCreatorId, String giftChainId) {
        super();
        //this.name = name;
        //this.url = url;
        //this.duration = duration;
        this.id = id;
        this.touches = touches;
        this.flagged = flagged;
        this.creationTime = creationTime;
        this.title = title;
        this.text = text;
        this.contentType = contentType;
        this.dataUrl = dataUrl;
        this.giftCreatorId = giftCreatorId;
        this.giftChainId = giftChainId;
    }

    //public String getName() {
    //    return name;
    //}

    //public void setName(String name) {
    //    this.name = name;
    //}

    //public String getUrl() {
    //   return url;
    //}

    //public void setUrl(String url) {
    //    this.url = url;
    //}

    //public long getDuration() {
    //    return duration;
    //}

    //public void setDuration(long duration) {
    //    this.duration = duration;
    //}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
  //added these
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty
    public String getDataUrl() {
        return dataUrl;
    }

    @JsonIgnore
    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getGiftCreatorId() {
        return giftCreatorId;
    }
    public void setGiftCreatorId(String giftCreatorId) {
        this.giftCreatorId = giftCreatorId;
    }
    public String getGiftChainId() {
        return giftChainId;
    }
    public void setGiftChainId(String giftChainId) {
        this.giftChainId = giftChainId;
    }

    public long getTouches() {
        return touches;
    }

    public void setTouches(long touches) {
        this.touches = touches;
    }

    public Set<String> getUserTouches() {
        return userTouches;
    }

    public long getFlagged(){
        return flagged;
    }

    public void setFlagged(long flagged) {
        this.flagged = flagged;
    }

    public Set<String> getUserFlagged() {
        return userFlagged;
    }

    public Date getCreationTime(){
        return creationTime;
    }

    public void setCreationTime(Date creationTime){
        this.creationTime = creationTime;
    }

    /**
     * Touched by the Gift
     * */
    public boolean touchedGift(String user) {
        if (user != null) {
            if (userTouches.add(user)) {
                ++touches;

                return true;
            }
        }

        return false;
    }


    /**
     * Untouched by the Gift
     * */
    public boolean untouchedGift(String user) {
        if (user != null) {
            if (userTouches.remove(user)) {
                --touches;
                return true;
            }
        }
        return false;

    }

    /**
     * Selected Gift as Obscene
     * */
    public boolean flaggedGift(String user) {
        if (user != null) {
            if (userFlagged.add(user)) {
                ++flagged;

                return true;
            }
        }

        return false;
    }

    /**
     * Untouched Gift as obscene_inappropriate
     * */
    public boolean unflaggedGift(String user) {
        if (user != null) {
            if (userFlagged.remove(user)) {
                --flagged;
                return true;
            }
        }
        return false;

    }

}

