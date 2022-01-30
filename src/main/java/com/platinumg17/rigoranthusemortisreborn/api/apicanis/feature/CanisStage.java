package com.platinumg17.rigoranthusemortisreborn.api.apicanis.feature;

//public class CanisStage {
//
//    private int stage;
//
//    public enum Stage {
//        CHORDATA("chordata", 1),
//        KYPHOS("kyphos", 2),
//        CAVALIER("cavalier", 3),
//        HOMINI("homini", 4);
//
//        String n;
//        int i;
//
//        Stage(String name, int stage) {
//            this.n = name;
//            this.i = stage;
//        }
//        public String getStageName() {
//            return this.n;
//        }
//        public int getStageNum() { return this.i; }
//    }
//
//    public CanisStage(int stage) {this.stage = stage;}
//
//
//    public int getStage(Stage stage) {return this.stage;}
//    public boolean canEvolve(Stage stage) {return stage != Stage.HOMINI || this.stage >= 4;}
//
//    @Deprecated
//    public void setStage(Stage type, int stageLvl) {return this.stage;}
//
//    @Deprecated
//    public void incrementStage(Stage stage) {
//        if(this.stage = null) {
//            this.setStage(stage, this.getStage(stage) + 1);
//        }
//        if(this.stage = Stage.CHORDATA) {
//            this.setStage(Stage.KYPHOS);
//        } else if(this.stage == Stage.KYPHOS) {
//            this.setStage(Stage.CAVALIER);
//        } else if(this.stage == Stage.CAVALIER) {
//            this.setStage(Stage.HOMINI);
//        }
//    }
//    public CanisStage copy() {return new CanisStage(this.stage);}
//    public final boolean isChordataCanis(Stage stage) {return stage == Stage.CHORDATA;}
//    public final boolean isKyphosCanis(Stage stage) {return stage == Stage.KYPHOS;}
//    public final boolean isCavalierCanis(Stage stage) {return stage == Stage.CAVALIER;}
//    public final boolean isHominiCanis(Stage stage) {return stage == Stage.HOMINI;}
//}