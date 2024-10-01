package com.kb.wallet.ticket.service;

import com.kb.wallet.member.domain.Member;
import com.kb.wallet.qrcode.dto.request.DecryptionRequest;
import com.kb.wallet.qrcode.dto.response.DecryptionResponse;
import com.kb.wallet.ticket.domain.Ticket;
import com.kb.wallet.ticket.dto.request.TicketExchangeRequest;
import com.kb.wallet.ticket.dto.request.TicketRequest;
import com.kb.wallet.ticket.dto.response.QrCreationResponse;
import com.kb.wallet.ticket.dto.response.TicketExchangeResponse;
import com.kb.wallet.ticket.dto.response.TicketResponse;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TicketService {

  Ticket findTicket(Long memberId, Long ticketId);

  Ticket findTicketById(Long id);

  Page<TicketResponse> findAllBookedTickets(String email, int page, int size);

  List<TicketResponse> saveTicket(String email, TicketRequest ticketRequest);

  void cancelTicket(String email, Long ticketId);

  boolean isTicketAvailable(Long memberId, Ticket ticket);

  void updateStatusChecked(Ticket ticket);

  TicketExchangeResponse createTicketExchange(Member member,
      TicketExchangeRequest exchangeRequest);

  void cancelTicketExchange(String email, Long ticketId);

  Page<TicketExchangeResponse> getUserExchangedTickets(Member member, int page, int size);

  DecryptionResponse useTicket(Member member, DecryptionRequest decryptionRequest) throws Exception;

  String getPrivateKey(Long ticketId);

  QrCreationResponse generateQRCodeData(String email, Long ticketId) throws Exception;

  void savePrivateKey(Long ticketId, String privateKey);

}
