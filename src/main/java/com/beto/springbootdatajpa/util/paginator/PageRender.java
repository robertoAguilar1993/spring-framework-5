package com.beto.springbootdatajpa.util.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;
    private Page<T> page;
    private int totalPaginas;
    private int numElementosPagina;
    private int paginaActual;
    private List<PageItem> paginas;

    /**
     *
     * @param url
     * @param page
     */
    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<>();
        numElementosPagina = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;
        int desde;
        int hasta;
        if(totalPaginas <= numElementosPagina){
            desde = 1;
            hasta = totalPaginas;
        }else{
            if(paginaActual <= numElementosPagina/2){
                desde = 1;
                hasta = numElementosPagina;
            }else if(paginaActual >= totalPaginas - numElementosPagina/2){
                desde = totalPaginas - numElementosPagina + 1;
                hasta = numElementosPagina;
            }else{
                desde = paginaActual - numElementosPagina/2;
                hasta = numElementosPagina;
            }
        }
        for(int i= 0;i<hasta;i++ ){
            paginas.add(new PageItem(desde + i, paginaActual==desde+i));
        }

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getNumElementosPagina() {
        return numElementosPagina;
    }

    public void setNumElementosPagina(int numElementosPagina) {
        this.numElementosPagina = numElementosPagina;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public void setPaginaActual(int paginaActual) {
        this.paginaActual = paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<PageItem> paginas) {
        this.paginas = paginas;
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }
    public  boolean isHasNext(){
        return  page.hasNext();
    }
    public  boolean isHasPrevious(){
        return  page.hasPrevious();
    }
}
