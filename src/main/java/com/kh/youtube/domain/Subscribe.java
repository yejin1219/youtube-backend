package com.kh.youtube.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Subscribe {

    @Id
    @Column(name="SUBS_CODE")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "subscribeSequence")
    @SequenceGenerator(name="subscribeSequence", sequenceName = "SEQ_SUBSCIBE", allocationSize=1)
    private int subscode;

    @Column(name="SUBS_DATE")
    private Date subsDate;

    @ManyToOne
    @JoinColumn(name="ID")
    private Member member;
    @ManyToOne
    @JoinColumn(name="CHANNEL_CODE")
    private Channel channel;


}
