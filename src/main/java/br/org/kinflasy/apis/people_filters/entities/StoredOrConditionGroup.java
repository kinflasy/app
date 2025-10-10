package br.org.kinflasy.apis.people_filters.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "conditions_or_group")
@Data
@EqualsAndHashCode(callSuper = true)
public class StoredOrConditionGroup extends StoredConditionGroup {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conditions_or_items", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<StoredCondition> conditions = new ArrayList<>();

}
