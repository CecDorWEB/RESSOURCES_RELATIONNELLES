document.addEventListener('DOMContentLoaded', () => {
    const elementsToReveal = document.querySelectorAll('[revealOnScroll]');
    const elementPositions = new Map();

    // Stockage des positions Y+1 des éléments
    elementsToReveal.forEach(element => {
        elementPositions.set(element, element.getBoundingClientRect().top + window.scrollY + 1);
        element.style.opacity = '0';
        element.style.transition = 'opacity 1s, transform 1s';
    });

    function checkScroll() {
        const scrollPosition = window.scrollY + window.innerHeight;

        elementPositions.forEach((position, element) => {
            if (scrollPosition > position) {
                const direction = element.getAttribute('revealOnScroll');

                if (direction === 'right' || direction === 'left') {
                    element.style.transform = `translateX(${direction === 'right' ? '100%' : '-100%'})`;
                    setTimeout(() => {
                        element.style.opacity = '1';
                        element.style.transform = 'translateX(0)';
                    }, 50);
                } else {
                    element.style.opacity = '1';
                }

                // Suppression de l'élément de la Map pour ne plus le vérifier
                elementPositions.delete(element);
            }
        });
    }

    window.addEventListener('scroll', checkScroll);
    // Vérification initiale au cas où des éléments seraient déjà visibles
    checkScroll();
});