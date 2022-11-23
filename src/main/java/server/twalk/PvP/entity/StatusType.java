package server.twalk.PvP.entity;

public enum StatusType {
    WAITING,
    MATCHED,
    FINISHED,
    FAILED,

    ///////////////
    승인, // jalking 진행 완료
    취소, //  jalking 주인이 취소
    진행, // jalking 멤버 모집 진행 중 / walking 진행
    완료 // walking 완료
}
