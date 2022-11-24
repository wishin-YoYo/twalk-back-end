package server.twalk.PvP.entity;

public enum StatusType {

    WAITING,
    MATCHED,
    FINISHED,
    FAILED,

    // jalking 관련 상태

    APPROVED, // jalking 완료
    CANCEL, // jalking 주인이 취소
    REJECTED, // jalking 취소
    ONGOING, // jalking 멤버 모집 ONGOING 중 / walking ONGOING
    COMPLETE  // walking 완료
}
