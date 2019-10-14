package com.javarush.task.task37.task3713.space.crew;

public abstract class AbstractCrewMember {
    public enum CompetencyLevel {
        NOVICE,
        INTERMEDIATE,
        ADVANCED,
        EXPERT
    }

    protected CompetencyLevel competencyLevel;

    protected AbstractCrewMember nextCrewMember;

    public void setNextCrewMember(AbstractCrewMember nextCrewMember) {
        this.nextCrewMember = nextCrewMember;
    }

    public void handleRequest(CompetencyLevel competencyLevel, String request) {
//        if (nextCrewMember.competencyLevel == CompetencyLevel.EXPERT) {
//            doTheJob(request);
//        } else if (nextCrewMember != null) {
//            nextCrewMember.handleRequest(competencyLevel, request);
//        }
        if(competencyLevel != this.competencyLevel){
            nextCrewMember.handleRequest(competencyLevel, request);
        }else {
            doTheJob(request);
        }
    }

    protected abstract void doTheJob(String request);
}
