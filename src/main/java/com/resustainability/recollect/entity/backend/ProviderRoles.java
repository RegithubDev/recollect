package com.resustainability.recollect.entity.backend;

import jakarta.persistence.*;

@Entity
@Table(name = ProviderRoles.TABLE_NAME)
public class ProviderRoles {
    public static final String TABLE_NAME = "backend_providerroles";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    
    public ProviderRoles() {
    	
    }

	public ProviderRoles(Long id, String roleName, Boolean isAdmin, Boolean isActive) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.isAdmin = isAdmin;
		this.isActive = isActive;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public static String getTableName() {
		return TABLE_NAME;
	}
    
    
}
