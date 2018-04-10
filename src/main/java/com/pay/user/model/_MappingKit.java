package com.pay.user.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {
	
	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("archives", "id", Archives.class);
		arp.addMapping("causation", "id", Causation.class);
		arp.addMapping("company", "id", Company.class);
		arp.addMapping("contract", "id", Contract.class);
		arp.addMapping("cooperation", "id", Cooperation.class);
		arp.addMapping("education", "id", Education.class);
		arp.addMapping("equipment", "id", Equipment.class);
		arp.addMapping("experience", "id", Experience.class);
		arp.addMapping("fillturnover", "id", Fillturnover.class);
		arp.addMapping("invoice", "id", Invoice.class);
		arp.addMapping("menu", "id", Menu.class);
		arp.addMapping("money", "id", Money.class);
		arp.addMapping("permission", "id", Permission.class);
		arp.addMapping("project", "id", Project.class);
		arp.addMapping("proportion", "id", Proportion.class);
		arp.addMapping("punish", "id", Punish.class);
		arp.addMapping("role", "id", Role.class);
		// Composite Primary Key order: mid,role_id
		arp.addMapping("role_menu", "mid,role_id", RoleMenu.class);
		// Composite Primary Key order: permission_id,role_id
		arp.addMapping("role_permission", "permission_id,role_id", RolePermission.class);
		arp.addMapping("scale", "id", Scale.class);
		arp.addMapping("theme", "id", Theme.class);
		arp.addMapping("turnover", "id", Turnover.class);
		arp.addMapping("user", "id", User.class);
		arp.addMapping("user_info", "user_id", UserInfo.class);
		// Composite Primary Key order: role_id,user_id
		arp.addMapping("user_role", "role_id,user_id", UserRole.class);
	}
}

