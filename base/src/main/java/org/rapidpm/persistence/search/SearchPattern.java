package org.rapidpm.persistence.search;

import org.apache.log4j.Logger;

import javax.persistence.*;

/**
 * RapidPM - www.rapidpm.org
 * User: Marco
 * Date: 09.04.13
 * Time: 09:59
 * This is part of the RapidPM - www.rapidpm.org project. please contact chef@sven-ruppert.de
 */
public class SearchPattern {

    private static final Logger logger = Logger.getLogger(SearchPattern.class);

    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String TITLE = "title";
    public static final String RATING = "rating";
    public static final String ALLTIMEVOTES = "allTimeVoteCount";
    public static final String ALLTIMESCORE ="allTimeScore";

    @Id
    @TableGenerator(name = "PKGenSearchPattern", table = "pk_gen", pkColumnName = "gen_key",
            pkColumnValue = "SearchPattern_id", valueColumnName = "gen_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "PKGenSearchPattern")
    private Long id;

    @Basic
    private String title;
    @Basic
    private String content;
    @Basic
    private Integer allTimeVoteCount;
    @Basic
    private Integer allTimeScore;
    @Transient
    private Double rating;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public Double getRating() {
        return (double) allTimeScore / (double) allTimeVoteCount;
    }

    public Integer getAllTimeVoteCount() {
        return allTimeVoteCount;
    }

    public void setAllTimeVoteCount(final Integer allTimeVoteCount) {
        this.allTimeVoteCount = allTimeVoteCount;
    }

    public Integer getAllTimeScore() {
        return allTimeScore;
    }

    public void setAllTimeScore(final Integer allTimeScore) {
        this.allTimeScore = allTimeScore;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SearchPattern that = (SearchPattern) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
