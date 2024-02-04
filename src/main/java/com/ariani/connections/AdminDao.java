package com.ariani.connections;
import com.ariani.dao.Admin;
import com.ariani.dao.Client;

import java.util.List;


public interface AdminDao {
   Admin getAdmin(String name,String pass);

}
