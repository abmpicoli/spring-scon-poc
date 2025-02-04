package br.nom.abmpicoli.spring_scon_poc.model;

/**
 * A Processor is an interface responsible for accepting a document in a specific format
 * and then perform a series of consumption steps, returning an output in another format.
 */
public interface Processor<INPUT_TYPE,OUTPUT_TYPE> {

    /**
     *
     * @param input the input data to feed the processing stream.
     * @return the output response, or null if no response is available.
     */
    OUTPUT_TYPE process(INPUT_TYPE input);

    

}
