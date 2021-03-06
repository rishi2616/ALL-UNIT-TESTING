package com.kingshuk.testing.testingwithranga.model.dao;


import com.kingshuk.testing.testingwithranga.model.TaskModel;
import com.kingshuk.testing.testingwithranga.model.utility.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TaskManagementDAOImpl implements TaskManagementDAO {

	private ConnectionManager connectionManager;

   
    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<TaskModel> getAllTask() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        List<TaskModel> taskModelList = new ArrayList<>();
        try {
            connection = connectionManager.getMyConnection();
            ps = connection.prepareStatement("SELECT * FROM mockito_test_task");
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                TaskModel taskModel = new TaskModel();
                taskModel.setTaskId(resultSet.getInt(1));
                taskModel.setTaskTitle(resultSet.getString(2));
                taskModel.setTaskDescription(resultSet.getString(3));
                taskModel.setTaskDueDate(resultSet.getString(4));
                taskModel.setTaskOwner(resultSet.getString(5));
                taskModelList.add(taskModel);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception sql) {
                sql.printStackTrace();
            }
        }

        return taskModelList;
    }

    @Override
    public boolean deleteTask(Integer taskId) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = connectionManager.getMyConnection();
            ps = connection.prepareStatement("DELETE FROM mockito_test_task WHERE task_id=?");
            ps.setInt(1, taskId);

            int delete = ps.executeUpdate();
            if(delete==0){
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {

                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (Exception sql) {
                sql.printStackTrace();
            }
        }
        return true;
    }



}
