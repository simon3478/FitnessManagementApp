package service;

import dao.MemberDAO;
import dao.ProgressDAO;
import dao.WorkoutDAO;
import entity.DashboardData;
import entity.Progress;
import entity.Workout;

import java.util.List;

public class DashboardService {

    private MemberDAO memberDAO;
    private ProgressDAO progressDAO;
    private WorkoutDAO workoutDAO;


    public DashboardService(MemberDAO memberDAO, ProgressDAO progressDAO, WorkoutDAO workoutDAO) {
        this.memberDAO = memberDAO;
        this.progressDAO = progressDAO;
        this.workoutDAO = workoutDAO;
        // Initialize other DAOs
    }

    public DashboardData getDashboardDataForUser(int memberId) {
        List<Progress> progress = progressDAO.findProgressByMemberId(memberId);
        List<Workout> workouts = workoutDAO.findWorkoutsByMemberId(memberId);
        // Fetch other data as needed

        DashboardData dashboardData = new DashboardData(progress, workouts);
        // You could manipulate the data further if needed before returning

        return dashboardData;
    }
}
