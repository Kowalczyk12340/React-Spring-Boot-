package com.example.backendblog.model.domain;

import com.example.backendblog.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "authority")
public class Authority extends BaseModel
{
    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 0,max = 20)
    @Id
    @Column(length = 20)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
        {
            return true;
        }
        if(obj == null || getClass() != obj.getClass())
        {
            return false;
        }

        Authority authority = (Authority) obj;

        return !(name != null ? !name.equals(authority.name) : authority.name != null);
    }
}
