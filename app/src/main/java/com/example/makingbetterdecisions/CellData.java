package com.example.makingbetterdecisions;

import java.util.ArrayList;
import java.util.List;

public class CellData {

    public static List<Cell> getHowToCells() { // need this to be in firebase
        List<Cell> cellList = new ArrayList<>();

        cellList.add(new Cell("Identify the Ethical Issues",
                "1. Could this decision or situation be damaging to someone or to some group, or unevenly beneficial to people? Does this decision involve a choice between a good and bad alternative, or perhaps between two “goods” or between two “bads”?" + "\n" +  "\n" +
                        "2. Is this issue about more than solely what is legal or what is most efficient? If so, how?"));
        cellList.add(new Cell("Get the Facts",
                "3. What are the relevant facts of the case? What facts are not known? Can I learn more about the situation? Do I know enough to make a decision?" + "\n" +  "\n" +"4. What individuals and groups have an important stake in the outcome? Are the concerns of some of those individuals or groups more important? Why?" + "\n" +"\n" +
                        "5. What are the options for acting? Have all the relevant persons and groups been consulted? Have I identified creative options?"));
        cellList.add(new Cell("Evaluate Alternative Actions",
                "6. Evaluate the options by asking the following questions:" + "\n" +  " • Which option best respects the rights of all who have a stake? (The Rights Lens)" + "\n" +  " • Which option treats people fairly, giving them each what they are due? (The Justice Lens)" + "\n" + " • Which option will produce the most good and do the least harm for as many stakeholders as possible? (The Utilitarian Lens)"+ "\n" +  " • Which option best serves the community as a whole, not just some members? (The Common Good Lens)" + "\n" + " • Which option leads me to act as the sort of person I want to be? (The Virtue Lens)" + "\n" + " • Which option appropriately takes into account the relationships, concerns, and feelings of all stakeholders? (The Care Ethics Lens)"));
        cellList.add(new Cell("Choose an Option for Action and Test It",
                "7. After an evaluation using all of these lenses, which option best addresses the situation?" + "\n" + "\n" + "8. If I told someone I respect (or a public audience) which option I have chosen, what would they say?" + "\n" +"\n" +
                        "9. How can my decision be implemented with the greatest care and attention to the concerns of all stakeholders?"));
        cellList.add(new Cell("Implement Your Decision and Reflect on the Outcome ",
                "10. How did my decision turn out, and what have I learned from this specific situation? What (if any) follow-up actions should I take?"));
        return cellList;
    }

    public static List<Cell> getLensCells(){
        List<Cell> cellList = new ArrayList<>();

        cellList.add(new Cell("THE RIGHTS LENS", "Some suggest that the ethical action is the one that best protects and respects the moral rights of those affected. This approach starts from the belief that humans have a dignity based on their human nature per se or on their ability to choose freely what they do with their lives. On the basis of such dignity, they have a right to be treated as ends in themselves and not merely as means to other ends. The list of moral rights— including the rights to make one's own choices about what kind of life to lead, to be told the truth, not to be injured, to a degree of privacy, and so on—is widely debated; some argue that non-humans have rights, too. Rights are also often understood as implying duties—in particular, the duty to respect others' rights and dignity."));
        cellList.add(new Cell("THE JUSTICE LENS", "Justice is the idea that each person should be given their due, and what people are due is often interpreted as fair or equal treatment. Equal treatment implies that people should be treated as equals according to some defensible standard such as merit or need, but not necessarily that everyone should be treated in the exact same way in every respect. There are different types of justice that address what people are due in various contexts. These include social justice (structuring the basic institutions of society), distributive justice (distributing benefits and burdens), corrective justice (repairing past injustices), retributive justice (determining how to appropriately punish wrongdoers), and restorative or transformational justice (restoring relationships or transforming social structures as an alternative to criminal punishment)."));
        cellList.add(new Cell("THE UTILITARIAN LENS", "Some ethicists begin by asking, “How will this action impact everyone affected?”—emphasizing the consequences of our actions. Utilitarianism, a results-based approach, says that the ethical action is the one that produces the greatest balance of good over harm for as many stakeholders as possible. It requires an accurate determination of the likelihood of a particular result and its impact. For example, the ethical corporate action, then, is the one that produces the greatest good and does the least harm for all who are affected—customers, employees, shareholders, the community, and the environment. Cost/benefit analysis is another consequentialist approach."));
        cellList.add(new Cell("THE COMMON GOOD LENS", "According to the common good approach, life in community is a good in itself and our actions should contribute to that life. This approach suggests that the interlocking relationships of society are the basis of ethical reasoning and that respect and compassion for all others—especially the vulnerable—are requirements of such reasoning. This approach also calls attention to the common conditions that are important to the welfare of everyone—such as clean air and water, a system of laws, effective police and fire departments, health care, a public educational system, or even public recreational areas. Unlike the utilitarian lens, which sums up and aggregates goods for every individual, the common good lens highlights mutual concern for the shared interests of all members of a community."));
        cellList.add(new Cell("THE VIRTUE LENS", "A very ancient approach to ethics argues that ethical actions ought to be consistent with certain ideal virtues that provide for the full development of our humanity. These virtues are dispositions and habits that enable us to act according to the highest potential of our character and on behalf of values like truth and beauty. Honesty, courage, compassion, generosity, tolerance, love, fidelity, integrity, fairness, self-control, and prudence are all examples of virtues. Virtue ethics asks of any action, “What kind of person will I become if I do this?” or “Is this action consistent with my acting at my best?”"));
        cellList.add(new Cell("THE CARE ETHICS LENS", "Care ethics is rooted in relationships and in the need to listen and respond to individuals in their specific circumstances, rather than merely following rules or calculating utility. It privileges the flourishing of embodied individuals in their relationships and values interdependence, not just independence. It relies on empathy to gain a deep appreciation of the interest, feelings, and viewpoints of each stakeholder, employing care, kindness, compassion, generosity, and a concern for others to resolve ethical conflicts. Care ethics holds that options for resolution must account for the relationships, concerns, and feelings of all stakeholders. Focusing on connecting intimate interpersonal duties to societal duties, an ethics of care might counsel, for example, a more holistic approach to public health policy that considers food security, transportation access, fair wages, housing support, and environmental protection alongside physical health."));


        return cellList;
    }
}
