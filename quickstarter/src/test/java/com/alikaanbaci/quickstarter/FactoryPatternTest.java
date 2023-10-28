package com.alikaanbaci.quickstarter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FactoryPatternTest {

    interface Assigner {

        String assign();
    }

    class IndividualAssigner implements Assigner {

        @Override
        public String assign() {
            return "Assign to individual";
        }
    }

    class TeamAssigner implements Assigner {

        @Override
        public String assign() {
            return "Assign to team";
        }
    }

    class WorkGroupAssigner implements Assigner {

        @Override
        public String assign() {
            return "Assign to work group";
        }
    }

    enum AssignmentType {
        INDIVIDUAL,
        TEAM,
        WORK_GROUP
    }

    class AssignerFactory {
        Assigner getAssigner(AssignmentType assignmentType) {
            return switch (assignmentType) {
                case INDIVIDUAL -> new IndividualAssigner();
                case TEAM -> new TeamAssigner();
                case WORK_GROUP -> new WorkGroupAssigner();
            };
        }
    }

    @Test
    void runTest() {
        // Prepare
        AssignerFactory assignerFactory = new AssignerFactory();
        var indAssigner = assignerFactory.getAssigner(AssignmentType.INDIVIDUAL);
        var teamAssigner = assignerFactory.getAssigner(AssignmentType.TEAM);
        var workGroupAssigner = assignerFactory.getAssigner(AssignmentType.WORK_GROUP);
        // Verify
        assertThat(indAssigner.assign()).isEqualTo("Assign to individual");
        assertThat(teamAssigner.assign()).isEqualTo("Assign to team");
        assertThat(workGroupAssigner.assign()).isEqualTo("Assign to work group");
    }
}
