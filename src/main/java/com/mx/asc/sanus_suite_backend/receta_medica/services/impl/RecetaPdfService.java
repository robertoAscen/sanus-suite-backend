package com.mx.asc.sanus_suite_backend.receta_medica.services.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mx.asc.sanus_suite_backend.medicos.entities.Medico;
import com.mx.asc.sanus_suite_backend.pacientes.entities.Paciente;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaDetalle;
import com.mx.asc.sanus_suite_backend.receta_medica.entities.RecetaMedica;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class RecetaPdfService {

  public ByteArrayInputStream generarPdfReceta(RecetaMedica receta, Paciente paciente, Medico medico) {
    Document document = new Document(PageSize.A4, 36, 36, 36, 36);
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    try {
      PdfWriter.getInstance(document, out);
      document.open();

      // Fuentes
      Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Color.DARK_GRAY);
      Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
      Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
      Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
      Font smallFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Color.GRAY);

      // 1. Encabezado de la Clínica / Médicos (Snapshot NOM-004)
      Paragraph titulo = new Paragraph("SANUS SUITE - RECETA MÉDICA", titleFont);
      titulo.setAlignment(Element.ALIGN_CENTER);
      document.add(titulo);

      String medicoNombre = receta.getMedicoNombreSnapshot() != null ? receta.getMedicoNombreSnapshot() :
        String.format("%s %s", medico.getNombre(), medico.getPrimerApellido());
      String cedula = receta.getMedicoCedulaSnapshot() != null ? receta.getMedicoCedulaSnapshot() : medico.getCedulaProfesional();

      Paragraph datosMedico = new Paragraph(String.format("Dr(a). %s | Céd. Prof: %s", medicoNombre, cedula), bodyFont);
      datosMedico.setAlignment(Element.ALIGN_CENTER);
      document.add(datosMedico);
      document.add(Chunk.NEWLINE);

      // 2. Datos del Paciente y Emisión
      PdfPTable infoTable = new PdfPTable(2);
      infoTable.setWidthPercentage(100);

      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      String fechaFmt = receta.getFechaEmision() != null ? receta.getFechaEmision().format(formatter) : "";

      infoTable.addCell(createNoBorderCell("Paciente: " + paciente.getNombre() + " " + paciente.getApellidoPaterno(), subTitleFont));
      infoTable.addCell(createNoBorderCell("Fecha: " + fechaFmt, bodyFont));
      infoTable.addCell(createNoBorderCell("Folio Receta: #" + receta.getId(), bodyFont));
      infoTable.addCell(createNoBorderCell("Estado: " + (receta.isFirmado() ? "FIRMADA DIGITALMENTE" : "BORRADOR"), bodyFont));

      document.add(infoTable);
      document.add(Chunk.NEWLINE);

      // 3. Tabla de Medicamentos Prescritos
      PdfPTable tablaMedicamentos = new PdfPTable(4);
      tablaMedicamentos.setWidthPercentage(100);
      tablaMedicamentos.setWidths(new float[]{35f, 20f, 20f, 25f});

      // Headers
      addHeaderCell(tablaMedicamentos, "Medicamento / Presentación", headerFont);
      addHeaderCell(tablaMedicamentos, "Dosis / Vía", headerFont);
      addHeaderCell(tablaMedicamentos, "Frecuencia / Duración", headerFont);
      addHeaderCell(tablaMedicamentos, "Indicaciones", headerFont);

      for (RecetaDetalle detalle : receta.getDetalles()) {
        String medInfo = detalle.getMedicamento() + (detalle.getPresentacion() != null ? " (" + detalle.getPresentacion() + ")" : "");
        String dosisVia = detalle.getDosis() + "\n" + detalle.getViaAdministracion();
        String freqDur = detalle.getFrecuencia() + "\n" + detalle.getDuracionTratamiento();

        tablaMedicamentos.addCell(new PdfPCell(new Phrase(medInfo, bodyFont)));
        tablaMedicamentos.addCell(new PdfPCell(new Phrase(dosisVia, bodyFont)));
        tablaMedicamentos.addCell(new PdfPCell(new Phrase(freqDur, bodyFont)));
        tablaMedicamentos.addCell(new PdfPCell(new Phrase(detalle.getIndicacionesAdicionales() != null ? detalle.getIndicacionesAdicionales() : "-", bodyFont)));
      }

      document.add(tablaMedicamentos);
      document.add(Chunk.NEWLINE);

      // 4. Observaciones Generales
      if (receta.getObservacionesGenerales() != null && !receta.getObservacionesGenerales().isBlank()) {
        Paragraph obsTitulo = new Paragraph("Observaciones Generales:", subTitleFont);
        Paragraph obsBody = new Paragraph(receta.getObservacionesGenerales(), bodyFont);
        document.add(obsTitulo);
        document.add(obsBody);
        document.add(Chunk.NEWLINE);
      }

      // 5. Pie de página y Firma Legal
      document.add(Chunk.NEWLINE);
      Paragraph lineaFirma = new Paragraph("_________________________________________", bodyFont);
      lineaFirma.setAlignment(Element.ALIGN_CENTER);
      document.add(lineaFirma);

      Paragraph firmaTxt = new Paragraph(String.format("Firma Legal del Médico\nCéd. Prof. %s", cedula), bodyFont);
      firmaTxt.setAlignment(Element.ALIGN_CENTER);
      document.add(firmaTxt);

      Paragraph nom = new Paragraph("Documento emitido en cumplimiento con la NOM-004-SSA3-2012 para Expediente Clínico Digital.", smallFont);
      nom.setAlignment(Element.ALIGN_CENTER);
      document.add(nom);

      document.close();

    } catch (DocumentException ex) {
      throw new RuntimeException("Error al generar el PDF de la receta", ex);
    }

    return new ByteArrayInputStream(out.toByteArray());
  }

  private PdfPCell createNoBorderCell(String text, Font font) {
    PdfPCell cell = new PdfPCell(new Phrase(text, font));
    cell.setBorder(Rectangle.NO_BORDER);
    cell.setPadding(4);
    return cell;
  }

  private void addHeaderCell(PdfPTable table, String text, Font font) {
    PdfPCell header = new PdfPCell(new Phrase(text, font));
    header.setBackgroundColor(Color.GRAY);
    header.setHorizontalAlignment(Element.ALIGN_CENTER);
    header.setPadding(6);
    table.addCell(header);
  }
}