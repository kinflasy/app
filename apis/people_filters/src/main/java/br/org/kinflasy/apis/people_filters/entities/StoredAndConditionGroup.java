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
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conditions_and_group")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StoredAndConditionGroup extends StoredConditionGroup {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "conditions_and_items", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<StoredCondition> conditions = new ArrayList<>();

}
